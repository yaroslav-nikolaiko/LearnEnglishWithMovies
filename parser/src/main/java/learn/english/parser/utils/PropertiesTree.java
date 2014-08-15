package learn.english.parser.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by yaroslav on 8/15/14.
 */
public class PropertiesTree {
    String filename;
    NavigableMap<String, String> data = new TreeMap<>();
    public PropertiesTree(String fileName){
        load(fileName);
    }


    public void load(String fileName) {
        InputStream inputStream = null;
        Properties properties = new Properties();
        try {
            inputStream = new FileInputStream(fileName);
            properties.load(inputStream);
            properties.forEach((k,v)->data.put(k.toString().toLowerCase(), v.toString()));
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
            Properties properties = new Properties();
            properties.putAll(data);
            properties.store(fileOut, description);
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

    /*
    return ranking if found word, or null if not.
    need to be refactored. use http://snowball.tartarus.org/demo.php to remove endings.
     */
    public String partialContains(String word){
        if (word==null)
            return null;
        if(data.containsKey(word))
            return data.get(word);
        if(word.length()<4)
            return  data.get(word);
        else if(word.length()==4 || word.length()==5) {
            Optional<Map.Entry<String, String>> min = data.entrySet().stream().filter(w -> w.getKey().toLowerCase().startsWith(word.substring(0, 3))).min((o1, o2) -> Math.abs(o1.getKey().compareToIgnoreCase(o2.getKey())));
            return min.isPresent() ?  min.get().getValue() : null;
        }else if(word.length()==6 || word.length()==7){
            Optional<Map.Entry<String, String>> min = data.entrySet().stream().filter(w -> w.getKey().toLowerCase().startsWith(word.substring(0, 4))).min((o1, o2) -> Math.abs(o1.getKey().compareToIgnoreCase(o2.getKey())));
            return min.isPresent() ?  min.get().getValue() : null;
        }else if(word.length()>7) {
            int lastIndex =Double.valueOf(word.length() * 0.7).intValue();
            Optional<Map.Entry<String, String>> min = data.entrySet().stream().filter(w -> w.getKey().toLowerCase().startsWith(word.substring(0, lastIndex))).min((o1, o2) -> Math.abs(o1.getKey().compareToIgnoreCase(o2.getKey())));
            return min.isPresent() ?  min.get().getValue() : null;
        }
        return null;
    }

}
