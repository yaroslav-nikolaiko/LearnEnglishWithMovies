package learn.english.core.service;

import learn.english.core.entity.Dictionary;
import learn.english.core.entity.MediaItem;
import learn.english.core.entity.WordCell;
import learn.english.core.entity.media.Book;
import learn.english.core.entity.media.Movie;
import learn.english.core.entity.media.Song;
import learn.english.core.entity.media.TVShow;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.core.utils.MediaItemType;
import learn.english.utils.LogTrace;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 6/17/14.
 */
@Stateless
@Path("/item")
@Produces({ MediaType.APPLICATION_JSON})
@Consumes({ MediaType.APPLICATION_JSON})
@ValidationHandlerEjb @LogTrace
public class MediaItemService extends AbstractService<MediaItem> {

    @EJB DictionaryService dictionaryService;


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

    public Set<WordCell> getUniqueWords(MediaItem item) {
        Set<WordCell> result = new HashSet<>(item.getWords());
        List<MediaItem> dictionaryMediaItems = new ArrayList<>(em.find(Dictionary.class, dictionaryService.getDictionary(item).getId()).getMediaItems());
        dictionaryMediaItems.remove(item);
        for (MediaItem i : dictionaryMediaItems)
            for (WordCell word : item.getWords())
                if(i.contains(word))
                    result.remove(word);
        return result;
    }

/*    public void destructor(MediaItem item){
        TypedQuery<WordCell> query = em.createNamedQuery(WordCell.GET_ALL_WORDS_WHICH_HAVE_ITEM, WordCell.class);
        query.setParameter(1, item);
        List<WordCell> wordCells = query.getResultList();
        for (WordCell word : wordCells) {
            word.removeMediaItem(item);
            if(word.getMediaItems().isEmpty())
                em.remove(word);
            else
                em.merge(word);
        }
    }*/

}
