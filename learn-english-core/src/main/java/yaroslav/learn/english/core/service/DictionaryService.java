package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentException;
import yaroslav.learn.english.core.interceptor.ValidationHandlerEjb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
@ValidationHandlerEjb
public class DictionaryService {
    @Inject
    EntityManager em;

    public void addMediaItem(@NotNull Dictionary dictionary, @NotNull MediaItem item) throws EJBIllegalArgumentException {
        String iName = item.getName();
        for (MediaItem i : dictionary.getMediaItems())
            if (i.getName().equals(iName))
                throw new EJBIllegalArgumentException(String.format("Media Item  with name = %s already exist", iName));
        dictionary.addMediaItem(item);
        em.persist(item);
        em.merge(dictionary);
    }

    public void removeMediaItems(@NotNull Dictionary dictionary, @NotNull Collection<MediaItem> items) throws EJBIllegalArgumentException {
        for (MediaItem item : items)
            removeMediaItem(dictionary, item);
    }

    public void removeMediaItem(@NotNull Dictionary dictionary, @NotNull MediaItem item) throws EJBIllegalArgumentException {
        dictionary.removeMediaItem(item);
        em.merge(dictionary);
        //em.remove(em.merge(item));
    }

    public void update(@NotNull Dictionary dictionary) {
        Dictionary managedDictionary =  em.merge(dictionary);
        dictionary.update(managedDictionary);
    }

}
