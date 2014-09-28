package learn.english.core.service;

import learn.english.model.dto.WordInfo;
import learn.english.translator.TranslatorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * Created by yaroslav on 9/6/14.
 */
@Stateless
@Path("translator")
public class TranslatorFacade {
    @EJB
    TranslatorManager translatorManager;
    @GET
    @Path("{text}")
    public String translate(@PathParam("text")String text,@QueryParam("from") String languageFrom,@QueryParam("to") String languageTo) {
       return translatorManager.translator(languageFrom, languageTo).translate(text);
    }

    @GET
    @Path("advance/{text}")
    public WordInfo advanceTranslate(@PathParam("text") String text, @QueryParam("from") String languageFrom, @QueryParam("to") String languageTo) {
        return translatorManager.translator(languageFrom, languageTo).singleWordTranslate(text);
    }
}
