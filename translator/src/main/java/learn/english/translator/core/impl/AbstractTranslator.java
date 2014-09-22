package learn.english.translator.core.impl;

import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.utils.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by yaroslav on 9/22/14.
 */
public abstract class AbstractTranslator  implements Translator {
    static final protected Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
    protected TranslatorDAO translatorDAO;
    @Override
    public String translate(String text) {
        text = text.toLowerCase();
        if (translatorDAO.contains(text))
            return translatorDAO.translation(text);
        logger.debug("Translator Vocabulary doesn't contain current text");
        List<String> textList = new ArrayList<>();
        textList.add(text);
        logger.info("Translate single text");
        List<String> list = getTranslation(textList);
        String result = list.get(0);
        logger.info("Translate result = {}",result);

        translatorDAO.save(text, result);

        return result;
    }

    @Override
    public void translateNewWords(Collection<String> textList) {
        if (textList == null || textList.isEmpty())
            return;
        List<String> newWords = textList.stream().map(w -> w.toLowerCase()).filter(w -> !translatorDAO.contains(w)).distinct().collect(toList());
        if (newWords.isEmpty()) {
            logger.info("Nothing to translate in translateNewWords");
            return;
        }

        logger.info("Prepare to translate partition. Input List size = {} / New Words size = {}",textList.size(), newWords.size());
        List<String> translated = getTranslation(newWords);
        if (newWords.size() != translated.size())
            throw new Error();
        for (int i = 0; i < newWords.size(); i++)
            translatorDAO.save(newWords.get(i), translated.get(i));
    }

    protected abstract List<String> getTranslation(List<String> textList);
}
