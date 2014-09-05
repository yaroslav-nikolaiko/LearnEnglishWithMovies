package learn.english.web.controller;

import learn.english.model.utils.Category;
import learn.english.model.utils.Language;
import learn.english.model.utils.Level;
import learn.english.model.utils.MediaItemType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.net.URI;

/**
 * Created by yaroslav on 9/5/14.
 */
@Named
@ApplicationScoped
public class GeneralPropertiesController implements Serializable {
    //URI uri = UriBuilder.fromUri("http://localhost/lingvo-movie-core/rest/general").port(8080).build();


    public  Language[] getAvailableLearningLanguages() {
/*        Client client = ClientBuilder.newClient();
        Response response = client.target(uri).path("learning").path("languages").request(MediaType.APPLICATION_JSON).get();
        return response.readEntity(Language[].class).val;*/
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
