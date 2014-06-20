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
        //Dictionary newDictionary = em.merge(dictionary);
        dictionary.addMediaItem(item);
        em.persist(item);
        //return newDictionary;
        //em.flush();
        //return newDictionary;
        //em.contains(dictionary);
        //Dictionary newDic = em.merge(dictionary);
        //return em.merge(dictionary);
    }



}
