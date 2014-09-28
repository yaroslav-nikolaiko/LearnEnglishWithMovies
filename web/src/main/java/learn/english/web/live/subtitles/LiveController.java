package learn.english.web.live.subtitles;

import learn.english.web.rest.RestService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created by yaroslav on 9/27/14.
 */
@Named
@SessionScoped
public class LiveController implements Serializable {
    @Inject LoginForm loginForm;
    @Inject RestService restService;

    String auth_token;

    public void login() {
        auth_token = restService.login(loginForm.getForm());
    }

    public boolean isNotLoggedIn(){
        return auth_token==null;
    }

/*    public void login(){
        Form form = new Form();
        form.param("name", name);
        form.param("password", password);
        Response response = this.path("user").path("login").target.request(MediaType.APPLICATION_JSON_TYPE).
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        auth_token = response.getHeaderString("auth_token");
        System.out.println("Login successful, auth_token = "+auth_token);
    }*/

}
