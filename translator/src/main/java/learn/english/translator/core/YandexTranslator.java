package learn.english.translator.core;

import learn.english.translator.Translator;
import learn.english.translator.XmlObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 8/10/14.
 */
public class YandexTranslator implements Translator{
    static final String API_KEY = "trnsl.1.1.20140531T124615Z.286c638c7c6d12c2.7b0a3f481468b227ad9021cbdc2bee694ec79d35";

    final String languageTo;
    final String languageFrom;
    //final String dataBaseName;
    Properties vocabulary;
    //static Map<String, Properties> vocabulary; //key=dataBaseName;



    public YandexTranslator(String languageFrom, String languageTo, Properties vocabulary){
        this.languageTo = languageTo;
        this.languageFrom = languageFrom;
        this.vocabulary = vocabulary;
        //dataBaseName = dataBaseName();
    }

    @Override
    public String translate(String text) {
        if(vocabulary.containsKey(text))
            return (String) vocabulary.get(text);
        try {
            List<String> textList = new ArrayList<>();
            textList.add(text);
/*            // *****************************
            byte[] bArray;
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(buildURL(textList).toURI());
            bArray = webTarget.request().get(byte[].class);
            List<String> list = unmarshaller(bArray).getText();
            // ******************************/
            List<String> list = unmarshaller(buildURL(textList)).getText();
            String result = list.get(0);
            vocabulary.put(text, result);
            return result;
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void translateLater(Collection<String> textList) {
        if(textList==null || textList.isEmpty())
            return;
        try {
            List<String> newWords = textList.stream().filter(w -> ! vocabulary.containsKey(w)).distinct().collect(toList());
            if(newWords.isEmpty()){
                System.out.println("Nothing to translate in YandexTranslator.translateLater");
                return;
            }

            List<String> translated = unmarshaller(buildURL(newWords)).getText();
            if(newWords.size()!=translated.size())
                throw new Error();
            for (int i=0; i<newWords.size(); i++)
                vocabulary.put(newWords.get(i), translated.get(i));

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String rootWord(String word) {
        return "";
    }

    @Override
    public String definition(String word) {
        return "";
    }


    YandexXMLObject unmarshaller(URL url){
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

/*    YandexXMLObject unmarshaller(byte[] content){
        YandexXMLObject result = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(content);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(YandexXMLObject.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (YandexXMLObject) jaxbUnmarshaller.unmarshal(bis);
            bis.close();
        }  catch (JAXBException | IOException e) {
            //TODO log
            e.printStackTrace();
        }
        return result;
    }*/

    URL buildURL(List<String> textList) throws UnsupportedEncodingException, MalformedURLException {
        String pattern = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s";
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
