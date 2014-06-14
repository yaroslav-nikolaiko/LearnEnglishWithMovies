package yaroslav.learn.english.web.controller;

import org.primefaces.context.RequestContext;
import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.exception.EntityIllegalArgumentsException;
import yaroslav.learn.english.core.service.DictionaryService;
import yaroslav.learn.english.core.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@SessionScoped
public class SessionController implements Serializable {
    @EJB    private UserService userService;
    @EJB    private DictionaryService dictionaryService;

    @Inject private UserBean userBean;
    @Inject private DictionaryBean dictionaryBean;
    @Inject private MediaItemBean mediaItemBean;

    private User user;
    private Dictionary currentDictionary;

    private List<MediaItem> selectedMediaItems;

    public String singUp(){
        user =  userBean.getUser();
        return "index";
    }

    public void login(){
        user =  userBean.login();
        if( user!=null && user.getDictionaries().size() > 0)
            currentDictionary = user.getDictionaries().get(0);
    }

    public void createDictionary(){
//        FacesContext context = FacesContext.getCurrentInstance();
//        FacesMessage message = null;
//        if(user!=null){
//            Dictionary dictionary = dictionaryBean.getDictionary();
//            try{
//                userService.addDictionary(user, dictionary);
//            }catch(EntityIllegalArgumentsException e){
//                message = new FacesMessage(e.getMessage());
//                context.addMessage(null,message);
//                return;
//            }
//            this.currentDictionary = dictionary;
//        }
//        else{
//            message = new FacesMessage(String.format("Please, Log in or Sign Up"));
//            context.addMessage(null,message);
//        }

            Dictionary dictionary = dictionaryBean.getDictionary();
            userService.addDictionary(user, dictionary);
            this.currentDictionary = dictionary;
    }

    public void loadMediaItem(){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        if(currentDictionary != null){
            MediaItem item = mediaItemBean.getMediaItem();
            dictionaryService.addMediaItem(currentDictionary, item);
        }
        else if(user==null){
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, String.format("Please, Log in or Sign Up"),"");
            context.addMessage(null,message);
            return;
        }else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,String.format("No dictionary selected"),"");
            context.addMessage(null,message);
        }

//        MediaItem item = mediaItemBean.getMediaItem();
//        dictionaryService.addMediaItem(currentDictionary, item);
    }

    public List<MediaItem> getSelectedMediaItems() {
        return selectedMediaItems;
    }

    public void setSelectedMediaItems(List<MediaItem> selectedMediaItems) {
        this.selectedMediaItems = selectedMediaItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    public void setCurrentDictionary(Dictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
    }

    @Produces
    public List<Dictionary> getAllDictionaries(){
        return user != null ? user.getDictionaries() : null;
    }
}
