package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentsException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
public class DictionaryService {
    @Inject
    EntityManager em;

    public void addMediaItem(@NotNull Dictionary dictionary,@NotNull MediaItem item) throws EJBIllegalArgumentsException {
        String iName = item.getName();
        for (MediaItem i : dictionary.getMediaItems())
            if (i.getName().equals(iName))
                throw new EJBIllegalArgumentsException(String.format("Media Item  with name = %s already exist", iName));
        dictionary.addMediaItem(item);
        //TODO: should I add validation user.id == getUserWithName(user.name).id ?
        if( ! em.contains(dictionary))
            em.persist(dictionary);
        else
            em.persist(item);
    }

}
