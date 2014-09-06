package learn.english.web.controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.net.URI;
import java.util.Map;

/**
 * Created by yaroslav on 9/6/14.
 */
public class RestService implements Serializable {
    private static final String SERVICE_HOST = "http://localhost/lingvo-movie-core/rest";
    private static final Integer SERVICE_PORT = 8080;
    private URI uri = UriBuilder.fromUri(SERVICE_HOST).port(SERVICE_PORT).build();
    private Client client = ClientBuilder.newClient();
    private WebTarget target;

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

    public <T> T get(Class<T> entityClass){
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

    public  Response delete(Long id){
        System.out.println("Post Query " + target.getUri());
        Response response = target.path(String.valueOf(id)).request().delete();
        target = null;
        return response;
    }

    public Long entityId(Response response){
        return Long.valueOf(response.getHeaderString("entity_id"));
    }

    public <T> Response update(T entity){
        System.out.println("PUT Query " + target.getUri());
        Response response = target.request().put(Entity.entity(entity, MediaType.APPLICATION_JSON));
        target = null;
        return response;
    }
}
