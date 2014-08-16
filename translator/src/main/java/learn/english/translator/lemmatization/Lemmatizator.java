package learn.english.translator.lemmatization;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 8/11/14.
 */
public interface Lemmatizator {
    static Lemmatizator instance(String language){

        System.setProperty("wordnet.database.dir", "/home/yaroslav/workspace/LearnEnglishWithMovies/translator/src/main/resources/jaws/dict");

        switch (language){
            default : return new WordNetDatabaseLemmatizator();
        }
    }
    String rootWord(String word);
    String stemForm(String word);
    String definition(String word);


    class WordNetDatabaseLemmatizator implements Lemmatizator{
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        SnowballProgram snowballSteamer = new EnglishStemmer();

        @Override
        public String rootWord(String word) {
            String[] baseFormCandidates = null;
            Synset[] synsets = database.getSynsets(word);
            if(synsets.length>0)
                baseFormCandidates = database.getBaseFormCandidates(word, synsets[0].getType());
            else
                return "";
            return baseFormCandidates.length > 0 ? baseFormCandidates[0] : "";

/*            Set<String> collect = (Set<String>) Arrays.asList(synsets).stream().
                    flatMap(s -> Arrays.asList(s.getWordForms()).stream()).collect(toSet());

            int compareResult = Integer.MIN_VALUE;
            String result = null;
            for (String s : collect) {
                int temp = s.compareTo(word);
                if (temp >= compareResult && temp < 0) {
                    result = s;
                    compareResult = s.compareTo(word);
                }
            }

            return result != null ? result : "";*/

            //return synsets.length > 0 ? synsets[0].getWordForms()[0] : "";

        }

        @Override
        public String definition(String word) {
            return null;
        }

        @Override
        public String stemForm(String word) {
            snowballSteamer.setCurrent(withoutEndingTLC(word));
            snowballSteamer.stem();
            return snowballSteamer.getCurrent();
        }

        private String withoutEndingTLC(String word){
            String toLowerCase = word.toLowerCase();
            if (toLowerCase.endsWith("n't"))
                return  toLowerCase.split("n't")[0];
            else
                return  toLowerCase.split("'")[0];
        }
    }
}
