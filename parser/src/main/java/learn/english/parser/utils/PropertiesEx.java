package learn.english.parser.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yaroslav on 8/12/14.
 */
public class PropertiesEx extends Properties {
    String filename;
    public PropertiesEx() {
        super();
    }
    public PropertiesEx(String fileName){
        super();
        load(fileName);
    }


    public void load(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            load(inputStream);
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
    }


    public void store(String description){
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filename);
            store(fileOut, description);
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
}
