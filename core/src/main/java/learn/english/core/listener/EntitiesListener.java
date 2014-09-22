package learn.english.core.listener;

import learn.english.core.service.ContentService;
import learn.english.core.service.DictionaryService;
import learn.english.core.service.MediaItemService;
import learn.english.model.listener.provider.EntitiesListenerProvider;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.translator.Translator;
import learn.english.translator.TranslatorManager;

import javax.ejb.EJB;

/**
 * Created by yaroslav on 9/19/14.
 */
public class EntitiesListener implements EntitiesListenerProvider {
    @EJB
    TranslatorManager translatorManager;
    @EJB
    DictionaryService dictionaryService;
    @EJB
    MediaItemService mediaItemService;
    @EJB
    ContentService contentService;

    @Override
    public void addTranslation(WordCell cell) {
        Dictionary dictionary = dictionaryService.getDictionary(cell);
        if(dictionary==null)
            return;
        String fromLanguage = dictionary.getLearningLanguage().toString();
        String toLanguage = dictionary.getNativeLanguage().toString();
        Translator translator = translatorManager.translator(fromLanguage, toLanguage);
        for (String word : cell.getWords()) {
            cell.getTranslation().put(word, translator.translate(word));
        }
    }

    @Override
    public void saveContentToDB(MediaItem item) {
        contentService.update(item.getId(), item.getContent());
        item.setContent(null);
        mediaItemService.update(item);
    }

    @Override
    public void removeContentFromDB(MediaItem item) {
        contentService.remove(item.getId());
    }

}
