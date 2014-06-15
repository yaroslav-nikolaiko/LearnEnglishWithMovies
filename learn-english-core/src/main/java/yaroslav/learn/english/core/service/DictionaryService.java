package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentsException;
import yaroslav.learn.english.core.interceptor.ValidationHandlerEjb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
@ValidationHandlerEjb
public class DictionaryService {
    @Inject
    EntityManager em;

    public Dictionary addMediaItem(@NotNull Dictionary dictionary, @NotNull MediaItem item) throws EJBIllegalArgumentsException{
        String iName = item.getName();
        for (MediaItem i : dictionary.getMediaItems())
            if (i.getName().equals(iName))
                throw new EJBIllegalArgumentsException(String.format("Media Item  with name = %s already exist", iName));
        dictionary.addMediaItem(item);
        //TODO: should I add validation user.id == getUserWithName(user.name).id ?
        em.persist(item);
        return em.merge(dictionary);

//        if( ! em.contains(dictionary))
//            em.persist(dictionary);
//        else
//            em.persist(item);
    }

}
