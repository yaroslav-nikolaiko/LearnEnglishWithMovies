package learn.english.translator.core.factory;

import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;

import java.io.Serializable;

/**
 * Created by yaroslav on 9/22/14.
 */
public interface TranslatorFactory extends Serializable {
    Translator getInstance(TranslatorDAO translatorDAO);
}
