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

    public  Language[] getAvailableLearningLanguages() {
        return Language.values();
    }

    public  Language[] getAvailableNativeLanguages(){
        return Language.values();
    }

    public Level[] getAvailableLevels(){
        return Level.values();
    }

    public Category[] getAvailableCategories(){
        return Category.values();
    }

    public MediaItemType[] getAvailableMediaItemTypes() {
        return MediaItemType.values();
    }
}
