package yaroslav.learn.english.web.controller;

import yaroslav.learn.english.core.entity.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
    @Inject
    private DictionaryController dictionaryController;
    private User user;

    public String singUp(){
        user =  userController.buildNewUser();
        return "index";
    }

    public String login(){
        User user = null;
        try{
            user =  userController.login();
            if(user==null){
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage("Name of password incorrect");
                context.addMessage(null,message);
                return null;
            }
        }catch(ValidatorException e){
            return null;
        }
        return "index";
    }

    public String createDictionary(){
        if(user==null){
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(String.format("Please, Log in or Sign Up"));
            context.addMessage(null,message);
            return null;
        }

        return "index";
    }

}
