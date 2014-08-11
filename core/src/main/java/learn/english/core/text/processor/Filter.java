package learn.english.core.text.processor;

import learn.english.core.utils.Language;
import learn.english.core.utils.Level;
import learn.english.translator.lemmatization.Lemmatizator;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

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
            register(w->w.matches(".*\\D.*"));  //doesn't contain digits.
            register(w->! w.equals("www") && ! w.equals("WWW"));

            // now tricky one ...about number of letters (in general). Could be not appropriate for all languages
            register(w -> w.length() > level.ordinal() || w.length() > 2);  //if basic >0, elementary >1, all other >2
        }
    }


    class EnglishFilter extends FilterChain{
        private static final String LANGUAGE = "en";
        Lemmatizator lemmatizator = Lemmatizator.instance(LANGUAGE);
        public EnglishFilter(Level level) {
            super(level);
        }

        @Override
        protected void languageSpecificFilters() {
            register(filterTrivialWords());

            register(w-> {
                String rootWord = lemmatizator.rootWord(w);
                if(! rootWord.isEmpty())
                    return rootWord.length() > level.ordinal() || rootWord.length() > 2;
                else{
                    if(w.endsWith("'s"))   //  words like he's
                        return w.substring(0, w.indexOf("'s")).length() > level.ordinal() || w.length() > 2;
                    if(w.endsWith("'t"))   //  words like don't
                        return w.substring(0, w.indexOf("'t")).length() > level.ordinal() || w.length() > 2;
                    return true;
                }
            });

/*            register(w->{
                if(w.endsWith("'s"))   //  words like he's
                    return w.substring(0, w.indexOf("'s")).length() > level.ordinal() || w.length() > 2;
                if(w.endsWith("'t"))   //  words like don't
                    return w.substring(0, w.indexOf("'t")).length() > level.ordinal() || w.length() > 2;
                return true;
            });*/
        }

        private Filter filterTrivialWords() {
            return w->{
                String rootWord = lemmatizator.rootWord(w);
                rootWord = rootWord.isEmpty() ? w : rootWord;
                return true;
            };
        }




    }
}
