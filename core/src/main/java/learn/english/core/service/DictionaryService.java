package learn.english.core.service;

import learn.english.core.entity.WordCell;
import learn.english.core.validation.ExistInDB;
import learn.english.core.entity.Dictionary;
import learn.english.core.entity.MediaItem;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.parser.Text;
import learn.english.parser.exception.ParserException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
@ValidationHandlerEjb
public class DictionaryService extends AbstractService<Dictionary> {

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
        processText(item);
        em.persist(item);
        em.merge(dictionary);
    }

    public void removeMediaItems(@ExistInDB Dictionary dictionary, Collection<MediaItem> items) throws EJBIllegalArgumentException {
        for (MediaItem item : items)
            removeMediaItem(dictionary, item);
    }

    public void removeMediaItem(@ExistInDB Dictionary dictionary, MediaItem item) throws EJBIllegalArgumentException {
        dictionary.removeMediaItem(item);
        em.merge(dictionary);
        //em.remove(em.merge(item));
    }

    private void processText(MediaItem item) throws EJBIllegalArgumentException {
        try {
            Text text = item.getParser().parse(item.getContent());
            Set<String> words = text.words();
            List<WordCell> cells = words.stream().map(WordCell::new).collect(toList());
            item.setWords(cells);
        } catch (ParserException e) {
            throw new EJBIllegalArgumentException("Error wile parsing text from media item", EJBIllegalArgumentException.MessageType.ERROR, e);
        }
    }

}
