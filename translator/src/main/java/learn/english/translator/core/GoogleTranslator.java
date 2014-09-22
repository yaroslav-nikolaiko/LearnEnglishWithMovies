package learn.english.translator.core;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import learn.english.translator.Translator;
import learn.english.utils.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*import org.springframework.data.mongodb.core.MongoTemplate;*/

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static java.util.stream.Collectors.toList;

/**
 * Created by yaroslav on 9/21/14.
 */
public class GoogleTranslator implements Translator{
    static final Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    static final String API_KEY = "trnsl.1.1.20140531T124615Z.286c638c7c6d12c2.7b0a3f481468b227ad9021cbdc2bee694ec79d35";
    static final int THRESHOLD = 1000;

    final String languageTo;
    final String languageFrom;

    DBCollection table;

    public GoogleTranslator(String languageFrom, String languageTo, DBCollection table){
        this.table = table;
        this.languageTo = languageTo;
        this.languageFrom = languageFrom;
    }

    @Override
    public String translate(String text) {
        text = text.toLowerCase();

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("text", text);
        DBObject one = table.findOne(searchQuery);
        if(one!=null)
            return (String) one.get("translation");

        logger.debug("Translator Vocabulary doesn't contain current text");
        try {
            List<String> textList = new ArrayList<>();
            textList.add(text);
            logger.debug("Translate single text with Yandex online translator REST API");
            List<String> list = unmarshaller(buildURL(textList)).getText();
            String result = list.get(0);
            logger.debug("Translate result = {}",result);
            BasicDBObject document = new BasicDBObject();
            document.put("text", text);
            document.put("translation", result);
            table.insert(document);
            return result;
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void translateNewWords(Collection<String> textList) {
        if (textList == null || textList.isEmpty())
            return;
        try {
            List<String> newWords = textList.stream().map(w->w.toLowerCase()).filter(w -> table.count(new BasicDBObject("text",w))==0).distinct().collect(toList());
            if (newWords.isEmpty()) {
                //System.out.println("Nothing to translate in YandexTranslator.translateNewWords");
                logger.debug("Nothing to translate in YandexTranslator.translateNewWords");
                return;
            }

            logger.debug("Prepare to translate partition with Yandex. Input List size = {} / New Words size = {}",textList.size(), newWords.size());
            List<String> translated = new ArrayList<>();
            for (List<String> partition : Lists.partition(newWords, THRESHOLD)) {
                logger.debug("Translate partition (size={}) with Yandex online translator REST API",partition.size());
                translated.addAll(unmarshaller(buildURL(partition)).getText());
            }
            if (newWords.size() != translated.size())
                throw new Error();
            for (int i = 0; i < newWords.size(); i++){
                BasicDBObject document = new BasicDBObject();
                document.put("text", newWords.get(i));
                document.put("translation", translated.get(i));
                table.insert(document);
            }

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    YandexXMLObject unmarshaller(URL url){
        logger.debug("Executing Yandex online translator REST API with URL = {}",url);
        YandexXMLObject result = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(YandexXMLObject.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (YandexXMLObject) jaxbUnmarshaller.unmarshal(url);
        }  catch (JAXBException e) {
            //TODO log
            e.printStackTrace();
        }
        return result;
    }


    URL buildURL(List<String> textList) throws UnsupportedEncodingException, MalformedURLException {
        /*String pattern = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s"; */
        String protocol = "https";
        String host = "translate.yandex.net";
        String webPathPattern = "/api/v1.5/tr/translate?key=%s&lang=%s&text=%s";
        String textSeparator = "&text=";

        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(URLEncoder.encode(textList.get(0), "UTF-8"));
        if (textList.size() > 1) {
            for (int i = 1; i <textList.size(); i++) {
                textBuilder.append(textSeparator + URLEncoder.encode(textList.get(i), "UTF-8"));
            }
        }

        String webPath = String.format(webPathPattern, API_KEY, languageTo, textBuilder);
        return new URL(protocol,host,webPath);
    }


    public static void main(String[] args) {
        String word = "rebelling";
        Translator translator = new YandexTranslator("en", "ru", new Properties());
        System.out.println(translator.translate(word));
    }
}
