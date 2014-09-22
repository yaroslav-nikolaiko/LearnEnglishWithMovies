package learn.english.translator.core.impl;

import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;

import java.util.Collection;

/**
 * Created by yaroslav on 9/22/14.
 */
public class GoogleTranslator implements Translator {
    TranslatorDAO translatorDAO;

    public GoogleTranslator(TranslatorDAO translatorDAO){
        this.translatorDAO = translatorDAO;
    }

    @Override
    public String translate(String text) {
        return null;
    }

    @Override
    public void translateNewWords(Collection<String> textList) {

    }
}
