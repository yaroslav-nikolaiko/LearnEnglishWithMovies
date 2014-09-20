package learn.english.core.service;


import learn.english.core.text.processor.TextProcessor;
import learn.english.core.validation.ExistInDB;

import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.User;
import learn.english.model.entity.WordCell;
import learn.english.utils.LogTrace;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @EJB UserService userService;
    @EJB MediaItemService mediaItemService;

    @Inject
    public DictionaryService(EntityManager em) {
        super(Dictionary.class);
        this.em = em;
    }

    public Dictionary getDictionary(MediaItem item) {
        return singeResult(Dictionary.FIND_BY_MEDIA_ITEM, item);
    }

    public Dictionary getDictionary(WordCell wordCell) {
        MediaItem mediaItem = mediaItemService.getMediaItem(wordCell);
        if(mediaItem==null)
            return null;
        return getDictionary(mediaItem);
    }


    @GET
    @Path("{id}")
    public Dictionary get(@PathParam("id") Long id) {
        return find(id);
    }

    @POST
    public Response addDictionary(@QueryParam("userID") Long userID, Dictionary dictionary) throws EJBIllegalArgumentException {
        User user = userService.find(userID);
        String dName = dictionary.getName();
        for (Dictionary d : user.getDictionaries())
            if (d.getName().equals(dName))
                throw new EJBIllegalArgumentException(String.format("Dictionary with name %s already exist", dName),
                        EJBIllegalArgumentException.MessageType.INFO);
        user.addDictionary(dictionary);
        //TODO: should I add validation user.id == getUserWithName(user.name).id ?
        Response response = addToDataBase(dictionary);
        em.merge(user);
        return response;
    }


    @DELETE
    @Path("{id}")
    public Response removeDictionary(@PathParam("id")Long id) {
        Dictionary dictionary = find(id);
        User user = userService.singeResult(User.FIND_BY_DICTIONARY, dictionary);
        user.removeDictionary(dictionary);
        garbageCollector(dictionary);
        return Response.noContent().build();
    }

    void garbageCollector( Dictionary dictionary){
        /*
        Here probably have some performance issue with multiple em.remove call. However commit will be after this method finished
        and before all operations with Persistence cache. I hope that JPA optimize this multiple deletion in the end.

        Anyway, this operation(deleting the dictionary) is very rare. But deleting an item is not so rare.
         */
        for (WordCell word : textProcessor.allWords(dictionary)) {
            em.remove(em.merge(word));}
    }


}
