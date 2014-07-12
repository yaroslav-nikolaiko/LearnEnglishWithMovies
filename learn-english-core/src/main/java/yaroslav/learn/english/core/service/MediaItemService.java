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
public class MediaItemService extends AbstractService<MediaItem> {

    @Inject
    public MediaItemService(EntityManager em) {
        super(MediaItem.class);
        this.em = em;
    }

    public boolean isExist(@NotNull MediaItem item){
        return find(item.getId())!=null;
    }

}
