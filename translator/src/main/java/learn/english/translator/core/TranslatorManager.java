package learn.english.translator.core;

import learn.english.translator.Translator;
import learn.english.utils.ConfigurationManager;
import learn.english.utils.LogTrace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yaroslav on 8/10/14.
 */
@Singleton
@Startup @LogTrace
public class TranslatorManager {
    static final String dataBaseFolder; //= "/home/yaroslav/workspace/LearnEnglishWithMovies/translator/src/main/resources/DB";
    static final Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    Map<String, Properties> dictionary = new HashMap<>();
    static {
        dataBaseFolder = System.getenv("LINGVO_MOVIE_PROJECT_FOLDER")+"translator/src/main/resources/DB";
    }

    @PostConstruct
    void init(){
        try {
            String http_proxy_str = System.getenv("http_proxy");
            String https_proxy_str = System.getenv("https_proxy");
            if(http_proxy_str!=null && ! http_proxy_str.isEmpty()){
                URI http_proxy = new URI(http_proxy_str);
                System.setProperty("http.proxyHost", http_proxy.getHost());
                System.setProperty("http.proxyPort", String.valueOf(http_proxy.getPort()));
                logger.info("Setting http.proxyHost = {}, http.proxyPort = {}",http_proxy.getHost(), http_proxy.getPort());
            }
            if(https_proxy_str!=null && ! https_proxy_str.isEmpty()){
                URI https_proxy = new URI(https_proxy_str);
                System.setProperty("https.proxyHost", https_proxy.getHost());
                System.setProperty("https.proxyPort", String.valueOf(https_proxy.getPort()));
                logger.info("Setting https.proxyHost = {}, https.proxyPort = {}",https_proxy.getHost(), https_proxy.getPort());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
        for (Map.Entry<String, Properties> entry : dictionary.entrySet())
            writeToFile(entry.getKey(), entry.getValue());

    }

    void writeToFile(String dataBaseName, Properties properties){
        FileOutputStream fileOut = null;
        try {
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
        File dbFile = new File(dataBaseFolder+"/"+databaseName);
        if( ! dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dbFile;
    }


}
