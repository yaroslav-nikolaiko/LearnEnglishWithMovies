package learn.english.translator.core.factory.impl;

import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.core.factory.TranslatorFactory;
import learn.english.translator.qualifier.service.Google;

/**
 * Created by yaroslav on 9/22/14.
 */
@Google
public class TranslatorFactoryGoogle implements TranslatorFactory {
    @Override
    public Translator getInstance(TranslatorDAO translatorDAO) {
        return null;
    }
}
