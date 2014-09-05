package learn.english.core.service;

import learn.english.model.utils.Category;
import learn.english.model.utils.Language;
import learn.english.model.utils.Level;
import learn.english.model.utils.MediaItemType;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by yaroslav on 6/12/14.
 */
@Singleton
@Named
@Lock(LockType.READ)
@Path("/general")
@Produces({ MediaType.APPLICATION_JSON})
@Consumes({ MediaType.APPLICATION_JSON})
public class GeneralProperties {

    @GET
    @Path("/learning/languages")
    public  Language[] getAvailableLearningLanguages() {
        return Language.values();
    }

    @GET
    @Path("/native/languages")
    public  Language[] getAvailableNativeLanguages(){
        return Language.values();
    }

    @GET
    @Path("/levels")
    public Level[] getAvailableLevels(){
        return Level.values();
    }

    @GET
    @Path("/categories")
    public Category[] getAvailableCategories(){
        return Category.values();
    }

    @GET
    @Path("/mediaItemTypes")
    public MediaItemType[] getAvailableMediaItemTypes() {
        return MediaItemType.values();
    }
}
