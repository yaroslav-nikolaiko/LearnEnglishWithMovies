package learn.english.translator.lemmatization;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * Created by yaroslav on 8/11/14.
 */
public interface Lemmatizator {
    static Lemmatizator instance(String language){
        switch (language){
            default : return new WordNetDatabaseLemmatizator();
        }
    }
    String rootWord(String word);
    String definition(String word);


    class WordNetDatabaseLemmatizator implements Lemmatizator{
        WordNetDatabase database = WordNetDatabase.getFileInstance();

        @Override
        public String rootWord(String word) {
            Synset[] synsets = database.getSynsets(word);
            return synsets.length > 0 ? synsets[0].getWordForms()[0] : "";

        }

        @Override
        public String definition(String word) {
            return null;
        }
    }
}
