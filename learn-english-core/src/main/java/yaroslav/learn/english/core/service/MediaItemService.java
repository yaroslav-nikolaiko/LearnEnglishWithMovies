package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentException;
import yaroslav.learn.english.core.interceptor.ValidationHandlerEjb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by yaroslav on 6/17/14.
 */
@Stateless
@ValidationHandlerEjb
public class MediaItemService {
    @Inject
    EntityManager em;

    public MediaItem findById(@NotNull Long id){
        return em.find(MediaItem.class, id);
    }

    public boolean isExist(@NotNull MediaItem item){
        return findById(item.getId())!=null;
    }

    public void removeMediaItems(@NotNull Collection<MediaItem> items) throws EJBIllegalArgumentException {
        for (MediaItem item : items)
            removeMediaItem(item);
    }

    public void removeMediaItem(@NotNull MediaItem item) throws EJBIllegalArgumentException{
        Dictionary dictionary = item.getDictionary();
        if( dictionary==null)
            throw new EJBIllegalArgumentException(String.format("You are trying to delete item %s which is not present in any dictionary" ,
                    item.toString()), EJBIllegalArgumentException.MessageType.ERROR  );
        dictionary.removeMediaItem(item);
        if (em.contains(item))
            em.remove(item);
        else if (isExist(item))
            em.remove(em.merge(item));
        //em.merge(item.getDictionary());
    }
}
