package learn.english.web.live.subtitles;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.Form;
import java.io.Serializable;

/**
 * Created by yaroslav on 9/27/14.
 */
@Named
@RequestScoped @NoArgsConstructor
public @Data class LoginForm implements Serializable {
    String name;
    String password;

    public Form getForm(){
        Form form = new Form();
        form.param("name", name);
        form.param("password", password);
        return form;
    }
}
