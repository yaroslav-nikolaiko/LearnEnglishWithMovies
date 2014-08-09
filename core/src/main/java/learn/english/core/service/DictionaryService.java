package learn.english.core.service;

import learn.english.core.entity.WordCell;
import learn.english.core.text.processor.TextProcessor;
import learn.english.core.validation.ExistInDB;
import learn.english.core.entity.Dictionary;
import learn.english.core.entity.MediaItem;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Set;


/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
@ValidationHandlerEjb
public class DictionaryService extends AbstractService<Dictionary> {
    @EJB TextProcessor textProcessor;

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
        for (WordCell word : textProcessor.allWords(dictionary)) {
            em.remove(em.merge(word));}
    }


}
