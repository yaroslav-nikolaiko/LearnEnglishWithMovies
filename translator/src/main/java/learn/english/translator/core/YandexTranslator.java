package learn.english.translator.core;

import com.google.common.collect.Lists;
import learn.english.translator.Translator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

/**
 * Created by yaroslav on 8/10/14.
 */
public class YandexTranslator implements Translator{
    static final String API_KEY = "trnsl.1.1.20140531T124615Z.286c638c7c6d12c2.7b0a3f481468b227ad9021cbdc2bee694ec79d35";
    static final int THRESHOLD = 1000;

    final String languageTo;
    final String languageFrom;
    Properties vocabulary;

    public YandexTranslator(String languageFrom, String languageTo, Properties vocabulary){
        this.languageTo = languageTo;
        this.languageFrom = languageFrom;
        this.vocabulary = vocabulary;
    }

    @Override
    public String translate(String text) {
        text = text.toLowerCase();
        if(vocabulary.containsKey(text))
            return (String) vocabulary.get(text);
        try {
            List<String> textList = new ArrayList<>();
            textList.add(text);
            //System.out.println("Translate single word with Yandex online translator REST api");
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
    public void translateNewWords(Collection<String> textList) {
        if (textList == null || textList.isEmpty())
            return;
        try {
            List<String> newWords = textList.stream().map(w->w.toLowerCase()).filter(w -> !vocabulary.containsKey(w)).distinct().collect(toList());
            if (newWords.isEmpty()) {
                //System.out.println("Nothing to translate in YandexTranslator.translateNewWords");
                return;
            }


            List<String> translated = new ArrayList<>();
            for (List<String> partition : Lists.partition(newWords, THRESHOLD)) {
                //System.out.println("Translate partition with Yandex online translator REST api");
                translated.addAll(unmarshaller(buildURL(partition)).getText());
            }
            if (newWords.size() != translated.size())
                throw new Error();
            for (int i = 0; i < newWords.size(); i++)
                vocabulary.put(newWords.get(i), translated.get(i));

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
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
