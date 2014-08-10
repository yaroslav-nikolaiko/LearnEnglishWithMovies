package learn.english.translator;

import java.util.Collection;
import java.util.List;

/**
 * Created by yaroslav on 8/6/14.
 */
public interface Translator {
    String translate(String text);
    void translateLater(Collection<String> textList);
    String rootWord(String word);
    String definition(String word);
}
