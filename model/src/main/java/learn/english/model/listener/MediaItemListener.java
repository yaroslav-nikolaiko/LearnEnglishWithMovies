package learn.english.model.listener;

import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.model.listener.provider.EntitiesListenerProvider;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

/**
 * Created by yaroslav on 9/21/14.
 */
public class MediaItemListener {
    //@Inject
    //@Resource(lookup = "java:global/lingvo-movie-core/EntitiesListener")
    EntitiesListenerProvider provider;

    @PostPersist
    public void saveContentToDB(MediaItem item) {
        try {
            InitialContext ic = new InitialContext();
            provider = (EntitiesListenerProvider)ic.lookup("java:global/lingvo-movie-core/EntitiesListener");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        provider.saveContentToDB(item);
    }

    @PostRemove
    public void removeContentFromDB(MediaItem item) {
        provider.removeContentFromDB(item);
    }

}
