package learn.english.core.service;

import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.text.processor.TextProcessor;
import learn.english.core.validation.ExistInDB;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.MediaItems;
import learn.english.model.entity.WordCell;
import learn.english.model.entity.media.Book;
import learn.english.model.entity.media.Movie;
import learn.english.model.entity.media.Song;
import learn.english.model.entity.media.TVShow;
import learn.english.model.utils.MediaItemType;
import learn.english.utils.LogTrace;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

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
    @EJB TextProcessor textProcessor;


    @Inject
    public MediaItemService(EntityManager em) {
        super(MediaItem.class);
        this.em = em;
    }

    public boolean isExist(MediaItem item){
        return find(item.getId())!=null;
    }


    @GET
    @Path("{id}")
    public MediaItem get(@PathParam("id") Long id) {
        return find(id);
    }

    @GET
    @Path("type/{type}")
    public MediaItem generateItem(@PathParam("type")MediaItemType type) {
        switch (type) {
            case TVSHOW:
                return new TVShow();
            case SONG:
                return new Song();
            case MOVIE:
                return new Movie();
            case BOOK:
                return new Book();
        }
        return null;
    }


    @POST
    public Response addMediaItem(@QueryParam("dictionaryID") Long dictionaryID, MediaItem item) throws EJBIllegalArgumentException {
        Dictionary dictionary = dictionaryService.find(dictionaryID);
        String iName = item.getName();
        for (MediaItem i : dictionary.getMediaItems())
            if (i.getName().equals(iName))
                throw new EJBIllegalArgumentException(String.format("Media Item  with name = %s already exist", iName));
        dictionary.addMediaItem(item);
        textProcessor.computeWordCells(item, dictionary);
        Response response = addToDataBase(item);
        em.merge(dictionary);
        return response;
    }

    @DELETE
    public Response removeMediaItems(@QueryParam("dictionaryID")Long dictionaryId, MediaItems items) throws EJBIllegalArgumentException {
        Dictionary dictionary = dictionaryService.find(dictionaryId);
        for (MediaItem item : items.getItems())
            removeMediaItem(dictionary, item);

        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response removeMediaItems(@QueryParam("dictionaryID")Long dictionaryId, @PathParam("id") Long id) throws EJBIllegalArgumentException {
        Dictionary dictionary = dictionaryService.find(dictionaryId);
        MediaItem item = find(id);
        removeMediaItem(dictionary, item);
        return Response.noContent().build();
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

}
