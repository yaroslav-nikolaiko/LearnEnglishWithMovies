package yaroslav.learn.english.web.controller;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentsException;
import yaroslav.learn.english.core.service.DictionaryService;
import yaroslav.learn.english.core.service.UserService;
import yaroslav.learn.english.web.interceptor.DialogValidation;
import yaroslav.learn.english.web.interceptor.ValidationHandler;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.ExcludeClassInterceptors;
import java.io.Serializable;
import java.util.*;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@SessionScoped
@ValidationHandler
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
        userService.add(user);
        return "index";
    }

    @DialogValidation
    public void login(){
        user =  userBean.login();
        if( user!=null && user.getDictionaries().size() > 0)
            currentDictionary = user.getDictionaries().get(0);
    }

    @DialogValidation
    public void createDictionary() throws EJBIllegalArgumentsException {

            Dictionary dictionary = dictionaryBean.getDictionary();
            userService.addDictionary(user, dictionary);
            this.currentDictionary = dictionary;

    }

    @DialogValidation
    public void loadMediaItem()throws EJBIllegalArgumentsException{
        MediaItem item = mediaItemBean.getMediaItem();
        byte[] content = mediaItemBean.getFile().getContents();
        item.setContent(content);
        dictionaryService.addMediaItem(currentDictionary, item);
    }

    /*********************************************************************************************
     **************************************** Getters and Setters ********************************
     *********************************************************************************************/

    @ExcludeClassInterceptors
    public List<MediaItem> getSelectedMediaItems() {
        return selectedMediaItems;
    }

    @ExcludeClassInterceptors
    public void setSelectedMediaItems(List<MediaItem> selectedMediaItems) {
        this.selectedMediaItems = selectedMediaItems;
    }

    @ExcludeClassInterceptors
    public User getUser() {
        return user;
    }

    @ExcludeClassInterceptors
    public void setUser(User user) {
        this.user = user;
    }

    @ExcludeClassInterceptors
    public Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    @ExcludeClassInterceptors
    public void setCurrentDictionary(Dictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
    }

    @Produces
    @ExcludeClassInterceptors
    public List<Dictionary> getAllDictionaries(){
        return user != null ? user.getDictionaries() : null;
    }
}
