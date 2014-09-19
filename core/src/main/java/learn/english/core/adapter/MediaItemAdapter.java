package learn.english.core.adapter;

import learn.english.core.service.DictionaryService;
import learn.english.model.adapter.provider.MediaItemAdapterProvider;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.translator.Translator;
import learn.english.translator.core.TranslatorManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yaroslav on 9/18/14.
 */
@Stateless
public class MediaItemAdapter implements MediaItemAdapterProvider{
    @Inject DictionaryService dictionaryService;
    @Inject TranslatorManager translatorManager;

    @Override
    public MediaItem unmarshal(MediaItem item) {
        return item;
    }

    @Override
    public MediaItem marshal(MediaItem item) {
        Dictionary dictionary = dictionaryService.getDictionary(item);
        String toLanguage = dictionary.getLearningLanguage().toString();
        String fromLanguage = dictionary.getNativeLanguage().toString();
        Translator translator = translatorManager.translator(fromLanguage, toLanguage);
        for (WordCell cell : item.getWords()) {
            for (String word : cell.getWords()) {
                cell.getTranslation().put(word, translator.translate(word));
            }
        }
        return item;
    }
}
