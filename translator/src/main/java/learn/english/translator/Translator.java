package learn.english.translator;

import java.util.Collection;

/**
 * Created by yaroslav on 8/6/14.
 */
public interface Translator {
    String translate(String text);
    void translateNewWords(Collection<String> textList);
    String rootWord(String word);
    String definition(String word);
}
