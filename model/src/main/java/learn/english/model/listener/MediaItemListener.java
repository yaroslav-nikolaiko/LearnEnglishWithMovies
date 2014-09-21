package learn.english.model.listener;

import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.model.listener.provider.EntitiesListenerProvider;

import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

/**
 * Created by yaroslav on 9/21/14.
 */
public class MediaItemListener {
    @Inject
    EntitiesListenerProvider provider;

    @PostPersist
    public void saveContentToDB(MediaItem item) {
         provider.saveContentToDB(item);
    }

    @PostRemove
    public void removeContentFromDB(MediaItem item) {
        provider.removeContentFromDB(item);
    }

}
