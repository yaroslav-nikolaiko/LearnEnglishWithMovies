package learn.english.core.service;

import learn.english.core.validation.ExistInDB;
import learn.english.core.entity.Dictionary;
import learn.english.core.entity.MediaItem;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
@ValidationHandlerEjb
public class DictionaryService extends AbstractService<Dictionary> {

    @Inject
    public DictionaryService(EntityManager em) {
        super(Dictionary.class);
        this.em = em;
    }

    public void addMediaItem(@ExistInDB Dictionary dictionary, MediaItem item) throws EJBIllegalArgumentException {
        String iName = item.getName();
        for (MediaItem i : dictionary.getMediaItems())
            if (i.getName().equals(iName))
                throw new EJBIllegalArgumentException(String.format("Media Item  with name = %s already exist", iName));
        processText(item);
        dictionary.addMediaItem(item);
        em.persist(item);
        em.merge(dictionary);
    }

    private void processText(MediaItem item) {

    }

    public void removeMediaItems(@ExistInDB Dictionary dictionary, Collection<MediaItem> items) throws EJBIllegalArgumentException {
        for (MediaItem item : items)
            removeMediaItem(dictionary, item);
    }

    public void removeMediaItem(@ExistInDB Dictionary dictionary, MediaItem item) throws EJBIllegalArgumentException {
        dictionary.removeMediaItem(item);
        em.merge(dictionary);
        //em.remove(em.merge(item));
    }

}
