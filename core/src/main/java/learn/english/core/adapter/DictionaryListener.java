package learn.english.core.adapter;

import learn.english.model.adapter.provider.DictionaryListenerProvider;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.translator.Translator;
import learn.english.translator.core.TranslatorManager;

import javax.inject.Inject;

/**
 * Created by yaroslav on 9/19/14.
 */
public class DictionaryListener implements DictionaryListenerProvider{
    @Inject
    TranslatorManager translatorManager;
    @Override
    public void addTranslation(Dictionary dictionary) {
        String fromLanguage = dictionary.getLearningLanguage().toString();
        String toLanguage = dictionary.getNativeLanguage().toString();
        Translator translator = translatorManager.translator(fromLanguage, toLanguage);
        for (MediaItem item : dictionary.getMediaItems()) {
            for (WordCell cell : item.getWords()) {
                for (String word : cell.getWords()) {
                    cell.getTranslation().put(word, translator.translate(word));
                }
            }
        }

    }
}
