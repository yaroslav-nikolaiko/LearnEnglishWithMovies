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

    @ValidationHandler @DialogValidation
    public void login(){
        user =  userBean.login();
        if( user!=null && user.getDictionaries().size() > 0)
            currentDictionary = user.getDictionaries().get(0);
    }

    @ValidationHandler @DialogValidation
    public void createDictionary() throws EJBIllegalArgumentsException {

            Dictionary dictionary = dictionaryBean.getDictionary();
            user = userService.addDictionary(user, dictionary);
            this.currentDictionary = dictionary;

    }

    @ValidationHandler @DialogValidation
    public void loadMediaItem()throws EJBIllegalArgumentsException{
        MediaItem item = mediaItemBean.getMediaItem();
        byte[] content = mediaItemBean.getFile().getContents();
        item.setContent(content);
        currentDictionary = dictionaryService.addMediaItem(currentDictionary, item);
    }

    /*********************************************************************************************
     **************************************** Getters and Setters ********************************
     *********************************************************************************************/

    @ExcludeClassInterceptors
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
