package learn.english.model.listener.provider;

import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;

/**
 * Created by yaroslav on 9/19/14.
 */
public interface EntitiesListenerProvider {
    void addTranslation(WordCell cell);
    void saveContentToDB(MediaItem item);
    void removeContentFromDB(MediaItem item);
}
