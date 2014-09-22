package learn.english.translator.core.impl.google;

import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.utils.Utils;

import java.util.Collection;

/**
 * Created by yaroslav on 9/22/14.
 */
public class GoogleTranslator implements Translator {
    TranslatorDAO translatorDAO;
    String languageFrom;
    String languageTo;

    public GoogleTranslator(TranslatorDAO translatorDAO){
        this.translatorDAO = translatorDAO;
        this.languageFrom = Utils.languageFrom(translatorDAO.getDBName());
        this.languageTo = Utils.languageTo(translatorDAO.getDBName());
    }

    @Override
    public String translate(String text) {
        return null;
    }

    @Override
    public void translateNewWords(Collection<String> textList) {

    }
}
