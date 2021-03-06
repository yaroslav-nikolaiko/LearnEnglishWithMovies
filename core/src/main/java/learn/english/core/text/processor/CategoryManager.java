package learn.english.core.text.processor;

import learn.english.model.entity.WordCell;
import learn.english.model.utils.Category;
import learn.english.model.utils.Language;
import learn.english.model.utils.Level;
import learn.english.translator.lemmatization.Lemmatizator;
import learn.english.utils.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Created by yaroslav on 8/12/14.
 */
public interface CategoryManager {
    static final Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    static CategoryManager instance(Language language){
        switch (language){
            default : return  new EnglishManager();
        }
    }

    Category calculate(WordCell word, Level level);

    class EnglishManager implements CategoryManager {
        private static final int ELEMENTARY_threshold = 400;
        private static final int INTERMEDIATE_threshold = 1000;
        private static final int UPPER_INTERMEDIATE_threshold = 2500;
        private static final int ADVANCED_threshold = 4000;
        private static final int FLUENT_threshold = Integer.MAX_VALUE;

        //PropertiesEx most_6000_common_words = new PropertiesEx(FOLDER_6000_MOST_COMMON_WORDS + "data.properties");
        Map<String, Integer> most_6000_common_words_root_forms = new HashMap<>();
        Lemmatizator lemmatizator = Lemmatizator.instance("en");

        public EnglishManager() {
            logger.debug("Construct English CategoryManager");
            Properties most_6000_common_words = ConfigurationManager.load("6000_most_common_words_file_path");
            for (Object o : most_6000_common_words.keySet()) {
                String word = (String) o;
                Integer ranking = Integer.valueOf((String) most_6000_common_words.get(o));
                String rootForm = lemmatizator.stemForm(word);
                Integer existedRanking = most_6000_common_words_root_forms.get(rootForm);
                if (existedRanking != null)
                    ranking = ranking < existedRanking ? ranking : existedRanking;
                most_6000_common_words_root_forms.put(rootForm, ranking);
            }

        }



        @Override
        public Category calculate(WordCell word, Level level) {
            switch (level) {
                case INTERMEDIATE : return calculate1(word, INTERMEDIATE_threshold);
                case UPPER_INTERMEDIATE : return calculate1(word, UPPER_INTERMEDIATE_threshold);
                case ADVANCED : return calculate1(word, ADVANCED_threshold);
                case FLUENT : return calculate1(word, FLUENT_threshold);
                case ELEMENTARY : return calculate1(word, ELEMENTARY_threshold);
                default : return Category.NEW_WORD;
            }
        }

        private Category calculate1(WordCell word, int threshold){
            // Look closely!  Need Refactor!
            String rootForm = word.getRootForm();
            Integer ranking = most_6000_common_words_root_forms.get(rootForm);

            if(ranking!=null && ranking<threshold)
                return Category.KNOWN;

            return Category.NEW_WORD;

        }


    }
}
