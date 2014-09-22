package learn.english.translator.core.impl.yandexsingleword;

import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.core.impl.AbstractTranslator;
import learn.english.translator.core.impl.yandex.YandexXMLObject;
import learn.english.translator.utils.Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yaroslav on 9/22/14.
 */
public class YandexSingleTranslator  {
    String languageTo;
    String languageFrom;

    public YandexSingleTranslator(TranslatorDAO translatorDAO, String DBName){
/*        this.translatorDAO = translatorDAO;
        this.languageTo = Utils.languageTo(DBName);
        this.languageFrom = Utils.languageFrom(DBName);*/
    }
    //@Override
    protected List<String> getTranslation(List<String> textList) {
        // Template http://dictionary.yandex.net/dicservice/lookup?&lang=en-ru&text=pal
        return null;
    }

    public YandexSingleXMLObject unmarshaller(URL url){
        //logger.info("Executing Yandex online translator REST API with URL = {}",url);
        YandexSingleXMLObject result = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(YandexSingleXMLObject.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (YandexSingleXMLObject) jaxbUnmarshaller.unmarshal(url);
        }  catch (JAXBException e) {
            //TODO log
            e.printStackTrace();
        }
        return result;
    }


    public URL buildURL() throws UnsupportedEncodingException, MalformedURLException {
        /*String pattern = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s"; */
        /*second option(works even better, but only for one word) http://dictionary.yandex.net/dicservice/lookup?&lang=en-ru&text=pal
          * for JSON use /dicservice.json/ */
        String protocol = "https";
        String host = "dictionary.yandex.net";
        String webPathPattern = "/dicservice/lookup?&lang=en-ru&text=pal";
        String textSeparator = "&text=";

/*        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(URLEncoder.encode(textList.get(0), "UTF-8"));
        if (textList.size() > 1) {
            for (int i = 1; i <textList.size(); i++) {
                textBuilder.append(textSeparator + URLEncoder.encode(textList.get(i), "UTF-8"));
            }
        }

        String webPath = String.format(webPathPattern, API_KEY, languageTo, textBuilder);*/


        String webPath = String.format(webPathPattern);
        return new URL(protocol,host,webPath);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {
        YandexSingleTranslator go = new YandexSingleTranslator(null,"2qwe");
        YandexSingleXMLObject result = go.unmarshaller(go.buildURL());
        System.out.println(result);
    }
}
