package learn.english.translator.core.factory.impl;

import learn.english.translator.Translator;
import learn.english.translator.core.factory.TranslatorFactory;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.core.impl.YandexTranslator;
import learn.english.translator.qualifier.service.Yandex;
import learn.english.translator.utils.Utils;

/**
 * Created by yaroslav on 9/22/14.
 */
@Yandex
public class TranslatorFactoryYandex implements TranslatorFactory {
    @Override
    public Translator getInstance(TranslatorDAO translatorDAO) {
        return new YandexTranslator(translatorDAO, Utils.languageTo(translatorDAO.getDBName()));
    }
}
