package learn.english.core.service;

import learn.english.core.entity.MediaItem;
import learn.english.core.entity.media.Book;
import learn.english.core.entity.media.Movie;
import learn.english.core.entity.media.Song;
import learn.english.core.entity.media.TVShow;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.core.utils.MediaItemType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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

    public boolean isExist(MediaItem item){
        return find(item.getId())!=null;
    }

    public MediaItem generateItem(MediaItemType type) {
        switch (type){
            case TVSHOW: return new TVShow();
            case   SONG: return new Song();
            case  MOVIE: return new Movie();
            case   BOOK: return new Book();
        }
        return null;
    }

}
