package learn.english.translator.core.dao.impl;

import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.utils.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by yaroslav on 9/22/14.
 */
public class TranslatorDAOFileCache implements TranslatorDAO {
    static final String dataBaseFolder; //= "/home/yaroslav/workspace/LearnEnglishWithMovies/translator/src/main/resources/DB";
    static final Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    //Map<String, Properties> dictionary = new HashMap<>();
    Properties dictionary;
    static {
        dataBaseFolder = System.getenv("LINGVO_MOVIE_PROJECT_FOLDER")+"translator/src/main/resources/DB";
    }

    String DBName;

    public TranslatorDAOFileCache(String DBName){
        this.DBName = DBName;
        dictionary = load(DBName);
    }

    @Override
    public boolean contains(String text) {
        return dictionary.containsKey(text);
    }

    @Override
    public void save(String text, String translation) {
        dictionary.put(text, translation);
    }

    @Override
    public String translation(String text) {
        return (String) dictionary.get(text);
    }

    @Override
    public String getDBName() {
        return DBName;
    }

    @Override
    public void onDestroy() {
        writeToFile(DBName, dictionary);
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
