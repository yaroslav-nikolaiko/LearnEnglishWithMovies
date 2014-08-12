import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by yaroslav on 8/12/14.
 */
public class ConvertToProperties {

    public static void main(String[] args) throws IOException {
        String folder1 = "/home/yaroslav/workspace/LearnEnglishWithMovies/core/src/main/resources/100_most_common_words/";
        String folder2 = "/home/yaroslav/workspace/LearnEnglishWithMovies/core/src/main/resources/6000_most_common_words/";
        String description1 = "en-ru 100 most common words";
        String description2 = "en-ru 6000 most common words";

        process(folder1, description1);
        process(folder2, description2);
    }

    private static void process(String folderName, String description) throws IOException{
        String filename = folderName + "data";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        Properties properties = new Properties();
        //int lastIndex = 0;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("\\s+");
            if (split.length != 2)
                throw new Error();
            //properties.put(String.valueOf(lastIndex++), split[1]);
            properties.put(split[1].toLowerCase(), split[0]);
        }
        br.close();

        properties.store(new FileOutputStream(folderName + "data.properties"), description);
    }

}
