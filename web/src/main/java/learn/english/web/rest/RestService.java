package learn.english.web.rest;

import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.User;
import learn.english.model.entity.WordCell;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by yaroslav on 9/6/14.
 */
public abstract class RestService <T> {
    private static final String SERVICE_HOST = "http://localhost/lingvo-movie-core/rest";
    private static final Integer SERVICE_PORT = 8080;
    protected URI uri = UriBuilder.fromUri(SERVICE_HOST).port(SERVICE_PORT).build();
    protected Client client = ClientBuilder.newClient();
    protected WebTarget target;

    String entityPath;
    Class<T> entityClass;

    public static <T> RestService instance(Class<T> entityClass){
        if(entityClass.equals(User.class))
            return new UserService<T>(entityClass);

        else if(entityClass.equals(Dictionary.class))
            return new DictionaryService<T>(entityClass);

        else if(entityClass.equals(MediaItem.class))
            return new MediaItemService<T>(entityClass);

        else if(entityClass.equals(WordCell.class))
            return new WordCellService<T>(entityClass);

        return null;
    }

    public RestService path(String path){
        if(target != null)
            target = target.path(path);
        else
            target = client.target(uri).path(path);
        return this;
    }

    public RestService param(String name, Object... values) {
        target = target.queryParam(name, values);
        return this;
    }

    public T get(Long id){
        target = client.target(uri).path(entityPath).path(String.valueOf(id));
        System.out.println("Get Query "+target.getUri());
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        target = null;
        return response.readEntity(entityClass);
    }

    public <T> Response post(T entity){
        System.out.println("Post Query "+target.getUri());
        Response response = target.request().post(Entity.entity(entity, MediaType.APPLICATION_JSON));
        target = null;
        return response;
    }

}
