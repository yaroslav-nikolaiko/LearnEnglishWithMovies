package learn.english.translator.core.impl.yandex;

import com.google.common.collect.Lists;
import learn.english.model.entity.WordInfo;
import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.core.impl.AbstractTranslator;
import learn.english.translator.utils.Utils;
import learn.english.utils.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

import static java.util.stream.Collectors.toList;

/**
 * Created by yaroslav on 9/22/14.
 */
public class YandexTranslator extends AbstractTranslator implements Translator{
    static final String API_KEY = "trnsl.1.1.20140531T124615Z.286c638c7c6d12c2.7b0a3f481468b227ad9021cbdc2bee694ec79d35";
    static final int THRESHOLD = 1000;
    String languageTo;
    YandexSingleWordTranslator singleWordTranslator;

    public YandexTranslator(TranslatorDAO translatorDAO){
        this.translatorDAO = translatorDAO;
        this.languageTo = Utils.languageTo(translatorDAO.getDBName());
        String languageFrom = Utils.languageFrom(translatorDAO.getDBName());
        singleWordTranslator = new YandexSingleWordTranslator(languageFrom, languageTo);
    }

    @Override
    protected List<String> getTranslation(List<String> textList) {
        List<String> translated = new ArrayList<>();
        try {
        for (List<String> partition : Lists.partition(textList, THRESHOLD)) {
            logger.info("Translate partition (size={}) with Yandex online translator REST API",partition.size());
            translated.addAll(unmarshaller(buildURL(partition)).getText());
        }
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return translated;
    }

    YandexXMLObject unmarshaller(URL url){
        logger.info("Executing Yandex online translator REST API with URL = {}",url);
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
        /*second option(works even better, but only for one word) http://dictionary.yandex.net/dicservice/lookup?&lang=en-ru&text=pal
          * for JSON use /dicservice.json/ */
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


    @Override
    public WordInfo singleWordTranslate(String text) {
        return singleWordTranslator.lookup(text);
    }
}
