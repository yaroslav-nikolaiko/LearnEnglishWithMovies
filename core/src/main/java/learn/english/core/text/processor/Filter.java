package learn.english.core.text.processor;

import learn.english.core.utils.Language;
import learn.english.core.utils.Level;
import learn.english.parser.utils.PropertiesEx;
import learn.english.translator.lemmatization.Lemmatizator;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 8/11/14.
 */
public interface Filter {
    static Filter instance(Language language, Level level){
        switch (language){
            default : return new EnglishFilter(level);
        }
    }

    boolean execute(@NotNull String word);

    // *****************************************************************************

    abstract class FilterChain implements Filter{
        List<Filter> chain = new LinkedList<>();
        Level level;

        public FilterChain(Level level){
            this.level = level;
            initialFilters();
            languageSpecificFilters();
        }

        protected abstract void languageSpecificFilters();

        @Override
        public boolean execute(@NotNull String word) {
            for (Filter filter : chain)
                if( ! filter.execute(word))
                    return false;
            return true;
        }

        protected void register(Filter next) {
            chain.add(next);
        }

        void initialFilters() {
            register(w->! w.matches(".*\\d.*"));  //doesn't contain digits.
            register(w->! w.equals("www") && ! w.equals("WWW"));

            // now tricky one ...about number of letters (in general). Could be not appropriate for all languages
            register(w -> w.length() > level.ordinal() || w.length() > 2);  //if basic >0, elementary >1, all other >2
        }
    }


    class EnglishFilter extends FilterChain{
        private static final String LANGUAGE = "en";
        private static final String FOLDER_100_MOST_COMMON_WORDS;//= "/home/yaroslav/workspace/LearnEnglishWithMovies/core/src/main/resources/100_most_common_words/";
        //PropertiesEx most_100_common_words = new PropertiesEx(FOLDER_100_MOST_COMMON_WORDS + "data.properties");
        Lemmatizator lemmatizator = Lemmatizator.instance(LANGUAGE);
        Set<String> most_100_common_words_rootForms = new HashSet<>();
        static{
            FOLDER_100_MOST_COMMON_WORDS = System.getenv("LINGVO_MOVIE_PROJECT_FOLDER")+"core/src/main/resources/100_most_common_words/";
        }

        public EnglishFilter(Level level) {
            super(level);
            PropertiesEx most_100_common_words = new PropertiesEx(FOLDER_100_MOST_COMMON_WORDS + "data.properties");
            for (Object o : most_100_common_words.keySet()) {
                String word = (String) o;
                String rootForm = lemmatizator.stemForm(word);
                most_100_common_words_rootForms.add(rootForm);
            }

        }

        @Override
        protected void languageSpecificFilters() {
            register(filterTrivialWords());

            register(w-> {
                String rootWord = lemmatizator.stemForm(w);
                return rootWord.length() > level.ordinal() || rootWord.length() > 2;
            });
        }

        private Filter filterTrivialWords() {
            return w -> ! most_100_common_words_rootForms.contains(lemmatizator.stemForm(w));
        }


    }
}
