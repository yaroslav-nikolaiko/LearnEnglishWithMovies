package learn.english.translator.lemmatization;

import edu.smu.tspell.wordnet.*;

/**
 * Created by yaroslav on 5/31/14.
 */
public class Main {
    /**
     * Main entry point. The command-line arguments are concatenated together
     * (separated by spaces) and used as the word form to look up.
     */
    public static void main(String[] args) {
        System.setProperty("wordnet.database.dir", "translator/src/main/resources/jaws/dict");
        String wordForm = "eaten";

        //  Get the synsets containing the wrod form
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(wordForm);

        //  Display the word forms and definitions for synsets retrieved
        if (synsets.length > 0) {
            System.out.println("The following synsets contain '" +
                    wordForm + "' or a possible base form " +
                    "of that text:");
            for (int i = 0; i < synsets.length; i++) {
                System.out.println("");
                String[] wordForms = synsets[i].getWordForms();
                for (int j = 0; j < wordForms.length; j++) {
                    System.out.print((j > 0 ? ", " : "") +
                            wordForms[j]);
                }
                System.out.println(": " + synsets[i].getDefinition());
            }
        } else {
            System.err.println("No synsets exist that contain " +
                    "the word form '" + wordForm + "'");
        }
    }
}
