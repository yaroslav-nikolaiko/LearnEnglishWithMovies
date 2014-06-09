package yaroslav.learn.english.web.controller;

import yaroslav.learn.english.core.entity.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@SessionScoped
public class SessionController implements Serializable {
    @Inject
    private UserController userController;
    private User user;

    public String singUp(){
        user =  userController.buildUser();
        return "index";
    }
}
