package learn.english.vlc;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by yaroslav on 9/25/14.
 */
public class ServerRestClient {
    private static final String SERVICE_HOST = "http://tevatron.ddns.ukrtel.net/lingvo-movie-core/rest";
    private static final Integer SERVICE_PORT = 80;
    protected URI uri = UriBuilder.fromUri(SERVICE_HOST).port(SERVICE_PORT).build();
    protected Client client = ClientBuilder.newClient();
    protected WebTarget target;
    String auth_token;

    private static final String name = "admin";
    private static final String password = "admin";


    public void execute(VlcStatusData data){
        if(auth_token==null)
            login();
        if(data!=null)
            this.path("live").update(data);
        else
            System.out.println("VlcStatusData = null. Please make sure VLC Server is running");
    }

    public void login(){
        Form form = new Form();
        form.param("name", name);
        form.param("password", password);
        Response response = this.path("user").path("login").target.request(MediaType.APPLICATION_JSON_TYPE).
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        auth_token = response.getHeaderString("auth_token");
        System.out.println("Login successful, auth_token = "+auth_token);
    }


    ServerRestClient path(String path){
        if(target != null)
            target = target.path(path);
        else
            target = client.target(uri).path(path);
        return this;
    }

    <T> Response update(T entity){
        //System.out.println("PUT Query " + target.getUri());
        Response response = target.request().header("auth_token", auth_token).put(Entity.entity(entity, MediaType.APPLICATION_JSON));
        target = null;
        return response;
    }
}
