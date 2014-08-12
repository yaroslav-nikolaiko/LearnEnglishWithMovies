package learn.english.core.text.processor;

import learn.english.core.entity.Dictionary;
import learn.english.core.entity.WordCell;
import learn.english.core.utils.Category;
import learn.english.core.utils.Language;
import learn.english.core.utils.Level;
import learn.english.parser.utils.PropertiesEx;
import learn.english.translator.lemmatization.Lemmatizator;

/**
 * Created by yaroslav on 8/12/14.
 */
public interface CategoryManager {
    static CategoryManager instance(Language language){
        switch (language){
            default : return  new EnglishManager();
        }
    }

    Category calculate(WordCell word, Level level);

    class EnglishManager implements CategoryManager {
        private static final String FOLDER_6000_MOST_COMMON_WORDS = "/home/yaroslav/workspace/LearnEnglishWithMovies/core/src/main/resources/6000_most_common_words/";
        private static final int ELEMENTARY_threshold = 200;
        private static final int INTERMEDIATE_threshold = 500;
        private static final int UPPER_INTERMEDIATE_threshold = 1000;
        private static final int ADVANCED_threshold = 3000;
        private static final int FLUENT_threshold = 6000;

        PropertiesEx most_6000_common_words = new PropertiesEx(FOLDER_6000_MOST_COMMON_WORDS + "data.properties");



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
            String rootWord = word.getRootWord();
            rootWord = rootWord.isEmpty() ? word.getWord() : rootWord;

            Object ranking_rootWord_Object = most_6000_common_words.get(rootWord.toLowerCase());
            Integer ranking_rootWord = ranking_rootWord_Object != null ? Integer.valueOf((String)ranking_rootWord_Object) : Integer.MAX_VALUE;

            Object ranking_word_Object = most_6000_common_words.get(word.getWord().toLowerCase());
            Integer ranking_word = ranking_word_Object != null ? Integer.valueOf((String)ranking_word_Object) : Integer.MAX_VALUE;

            if(ranking_rootWord < threshold  || ranking_word<threshold)
                return Category.KNOWN;
            else
                return Category.NEW_WORD;
        }
    }
}
