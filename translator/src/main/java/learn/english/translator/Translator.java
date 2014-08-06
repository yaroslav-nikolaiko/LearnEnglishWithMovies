package learn.english.translator;

/**
 * Created by yaroslav on 8/6/14.
 */
public interface Translator {
    String translate(String text);
    String rootWord(String word);
    String definition(String word);
}
