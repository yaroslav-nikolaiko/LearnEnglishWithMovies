package yaroslav.learn.english.web.controller;

import org.primefaces.context.RequestContext;
import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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
    @Inject
    private MediaItemBean mediaItemBean;
    private User user;
    private Dictionary currentDictionary;

    private List<MediaItem> selectedMediaItems;

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

    public void loadMediaItem(){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        if(currentDictionary != null){
            currentDictionary.addMediaItem(mediaItemBean.getMediaItem());
            userService.merge(user);
            return;
        }
        else if(user==null){
            message = new FacesMessage(String.format("Please, Log in or Sign Up"));
            context.addMessage(null,message);
        }else{
            message = new FacesMessage(String.format("No dictionary found"));
            context.addMessage(null,message);
        }
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
        return user!=null ? user.getDictionaries() : null;
    }

    public List<SelectItem> getSelectItems(){

        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        if(user==null)
            return selectItems;
        for (Dictionary dict : user.getDictionaries()) {
            selectItems.add(new SelectItem(dict, dict.getName()));
        }
        return selectItems;
    }
}
