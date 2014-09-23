package learn.english.translator.core.impl.yandex;

import learn.english.model.entity.WordInfo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yaroslav on 9/22/14.
 */
public class YandexSingleWordTranslator {
    String languageTo;
    String languageFrom;

    public YandexSingleWordTranslator(String languageFrom, String languageTo){
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
    }

    public WordInfo lookup(String text) {
        // Template http://dictionary.yandex.net/dicservice/lookup?&lang=en-ru&text=pal
        try {
            return unmarshaller(buildURL(text));
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WordInfo unmarshaller(URL url){
        WordInfo result = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(WordInfo.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (WordInfo) jaxbUnmarshaller.unmarshal(url);
        }  catch (JAXBException e) {
            //TODO log
            e.printStackTrace();
        }
        return result;
    }


    public URL buildURL(String text) throws UnsupportedEncodingException, MalformedURLException {
        /*String pattern = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s"; */
        /*second option(works even better, but only for one word) http://dictionary.yandex.net/dicservice/lookup?&lang=en-ru&text=pal
          * for JSON use /dicservice.json/ */
        text = URLEncoder.encode(text, "UTF-8");
        String protocol = "http";
        String host = "dictionary.yandex.net";
        String language = languageFrom+"-"+languageTo;
        String webPath = String.format("/dicservice/lookup?&lang=%s&text=%s", language, text);
        return new URL(protocol,host,webPath);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {
        YandexSingleWordTranslator go = new YandexSingleWordTranslator("ru", "en");
        WordInfo result = go.unmarshaller(go.buildURL("жара"));
        System.out.println(result);
    }
}
