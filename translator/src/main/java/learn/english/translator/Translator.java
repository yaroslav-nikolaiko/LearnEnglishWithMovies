package learn.english.translator;

import learn.english.model.entity.WordInfo;

import java.util.Collection;

/**
 * Created by yaroslav on 8/6/14.
 */
public interface Translator {
    String translate(String text);
    WordInfo singleWordTranslate(String text);
    void translateNewWords(Collection<String> textList);
}
