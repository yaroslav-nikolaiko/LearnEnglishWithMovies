package learn.english.core.service;


import learn.english.core.text.processor.TextProcessor;
import learn.english.core.validation.ExistInDB;

import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.utils.LogTrace;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Set;


/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
@Path("/dict")
@Produces({ MediaType.APPLICATION_JSON})
@Consumes({ MediaType.APPLICATION_JSON})
@ValidationHandlerEjb @LogTrace
public class DictionaryService extends AbstractService<Dictionary> {
    @EJB TextProcessor textProcessor;

    @Inject
    public DictionaryService(EntityManager em) {
        super(Dictionary.class);
        this.em = em;
    }

    public Dictionary getDictionary(MediaItem item) {
        return singeResult(Dictionary.FIND_BY_MEDIA_ITEM, item);
    }

    public void addMediaItem(@ExistInDB Dictionary dictionary, MediaItem item) throws EJBIllegalArgumentException {
        String iName = item.getName();
        for (MediaItem i : dictionary.getMediaItems())
            if (i.getName().equals(iName))
                throw new EJBIllegalArgumentException(String.format("Media Item  with name = %s already exist", iName));
        dictionary.addMediaItem(item);
        em.persist(item);
        textProcessor.computeWordCells(item,dictionary);
        em.merge(dictionary);
    }

    public void removeMediaItems(@ExistInDB Dictionary dictionary, Collection<MediaItem> items) throws EJBIllegalArgumentException {
        for (MediaItem item : items)
            removeMediaItem(dictionary, item);
    }

    public void removeMediaItem(@ExistInDB Dictionary dictionary, MediaItem item) throws EJBIllegalArgumentException {
        dictionary.removeMediaItem(item);
        garbageCollector(item, dictionary);
        em.merge(dictionary);
        //em.remove(em.merge(item));
    }

    void garbageCollector(MediaItem item, Dictionary dictionary){
        // garbage collector (remove word which have no more reference at all)
        Set<WordCell> words = item.getWords();
        for (WordCell word : words) {
            boolean noMoreReferenceForWord = true;
            for (MediaItem i : dictionary.getMediaItems()) {
                if(i.contains(word)){
                    noMoreReferenceForWord = false;
                    break;
                }
            }
            if( noMoreReferenceForWord )
                em.remove(em.merge(word));

        }
    }

    public void garbageCollector( Dictionary dictionary){
        /*
        Here probably have some performance issue with multiple em.remove call. However commit will be after this method finished
        and before all operations with Persistence cache. I hope that JPA optimize this multiple deletion in the end.

        Anyway, this operation(deleting the dictionary) is very rare. But deleting an item is not so rare.
         */
        for (WordCell word : textProcessor.allWords(dictionary)) {
            em.remove(em.merge(word));}
    }


}
