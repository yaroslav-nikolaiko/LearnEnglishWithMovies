package learn.english.core.service;

import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 9/5/14.
 */
public class WordCellService extends AbstractService<WordCell> {
    @EJB MediaItemService mediaItemService;
    @EJB DictionaryService dictionaryService;

    @Inject
    public WordCellService(EntityManager em) {
        super(WordCell.class);
        this.em = em;
    }

    @GET
    @Path("unique/{itemID}")
    public GenericEntity<Set<WordCell>> getUniqueWords(@PathParam("itemID")Long mediaItemID) {
        MediaItem item = mediaItemService.find(mediaItemID);
        Set<WordCell> result = new HashSet<>(item.getWords());
        List<MediaItem> dictionaryMediaItems = new ArrayList<>(em.find(Dictionary.class, dictionaryService.getDictionary(item).getId()).getMediaItems());
        dictionaryMediaItems.remove(item);
        for (MediaItem i : dictionaryMediaItems)
            for (WordCell word : item.getWords())
                if (i.contains(word))
                    result.remove(word);
        return new GenericEntity<Set<WordCell>>(result) {
        };
    }

}
