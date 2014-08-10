package learn.english.translator.core;

import learn.english.translator.Translator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yaroslav on 8/10/14.
 */
@Singleton
@Startup
public class TranslatorManager {
    static final String dataBaseFolder = "/home/yaroslav/workspace/LearnEnglishWithMovies/translator/src/main/resources/DB";
    Map<String, Properties> dictionary = new HashMap<>();


    @PostConstruct
    void init(){
        System.getProperties().put("https.proxyHost", "proxy2.cht");
        System.getProperties().put("https.proxyPort", "3128");
        System.getProperties().put("http.proxyHost", "proxy2.cht");
        System.getProperties().put("http.proxyPort", "3128");
    }

    public Translator translator(String languageFrom, String languageTo) {
        String dataBaseName = dataBaseName(languageFrom, languageTo);
        Properties vocabulary = dictionary.get(dataBaseName);
        if(vocabulary==null) {
            vocabulary = load(dataBaseName);
            dictionary.put(dataBaseName, vocabulary);
        }

        return new YandexTranslator(languageFrom, languageTo, vocabulary);
    }

    @PreDestroy
    void persist() {
        System.out.println("Destroy TranslatorManager");
        for (Map.Entry<String, Properties> entry : dictionary.entrySet())
            writeToFile(entry.getKey(), entry.getValue());

    }

    void writeToFile(String dataBaseName, Properties properties){
        System.out.println("Persist dictionary database "+dataBaseName);
        FileOutputStream fileOut = null;
        try {
            //URLConnection connection = dataBaseURL(dataBaseName).openConnection();
            //connection.setDoOutput(true);
            //fileOut = connection.getOutputStream();
            fileOut = new FileOutputStream(dataNameFile(dataBaseName));
            properties.store(fileOut, dataBaseName);
        } catch (IOException e) {
            //logger.error(MessageFormat.format("Failed to read {0} file", fullFilename), e);
            e.printStackTrace();
        } finally {
            if(fileOut!=null){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    //logger.warn(MessageFormat.format("Failed to close {0} file", fullFilename), e);
                    e.printStackTrace();
                }
            }
        }
    }

    Properties load(String  dataBaseName) {
        Properties property = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = dataBaseURL(dataBaseName).openStream();
            property.load(inputStream);
        } catch (IOException ex) {
            //throw new IOException(MessageFormat.format("Problem with loading default configurations file {0} ", filename), ex);
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //logger.warn(MessageFormat.format("Failed to close {0} file", filename), e);
                    e.printStackTrace();
                }
            }
        }
        return property;
    }

    String dataBaseName(String languageFrom, String languageTo){
        return languageFrom+"-"+languageTo;
    }

    URL dataBaseURL(String databaseName){
        try {
            return dataNameFile(databaseName).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    File dataNameFile(String databaseName){
        return new File(dataBaseFolder+"/"+databaseName);
    }


}
