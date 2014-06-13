package yaroslav.learn.english.web.controller;

import org.primefaces.context.RequestContext;
import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@SessionScoped
public class SessionController implements Serializable {
    @EJB
    private UserService userService;
    @Inject
    private UserBean userBean;
    @Inject
    private DictionaryBean dictionaryBean;
    private User user;
    private Dictionary currentDictionary;

    public String singUp(){
        user =  userBean.getUser();
        return "index";
    }

    public void login(){
        user =  userBean.login();
    }

    public void createDictionary(){
        if(user!=null){
            this.currentDictionary = dictionaryBean.getDictionary();
            user.addDictionary(currentDictionary);
            userService.merge(user);
        }
        if(user==null){
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(String.format("Please, Log in or Sign Up"));
            context.addMessage(null,message);
        }

    }

    public Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    public void setCurrentDictionary(Dictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
    }

    public List<Dictionary> getAllDictionaries(){
        return user!=null ? user.getDictionaries() : null;
    }
}
