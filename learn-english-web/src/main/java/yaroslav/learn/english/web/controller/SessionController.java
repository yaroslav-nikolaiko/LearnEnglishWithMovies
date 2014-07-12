package yaroslav.learn.english.web.controller;

import org.apache.commons.io.IOUtils;
import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentException;
import yaroslav.learn.english.core.service.DictionaryService;
import yaroslav.learn.english.core.service.MediaItemService;
import yaroslav.learn.english.core.service.UserService;
import yaroslav.learn.english.web.interceptor.DialogValidation;
import yaroslav.learn.english.web.interceptor.ValidationHandler;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.ExcludeClassInterceptors;
import java.io.IOException;
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
    @EJB    private MediaItemService mediaItemService;

    @Inject private UserBean userBean;
    @Inject private DictionaryBean dictionaryBean;
    @Inject private MediaItemBean mediaItemBean;

    private User user;
    private Dictionary currentDictionary;

    private List<MediaItem> selectedMediaItems;

    void init(){
        currentDictionary = null;
        selectedMediaItems = new ArrayList<>();
    }

    public String singUp(){
        user =  userBean.getUser();
        userService.addToDataBase(user);
        return "index?faces-redirect=true";
    }

    @ValidationHandler @DialogValidation
    public void login(){
        init();
        user =  userBean.login();
        if( user!=null && user.getDictionaries().size() > 0)
            currentDictionary = user.getDictionaries().get(0);
    }

    @ValidationHandler
    @DialogValidation
    public void createDictionary() throws EJBIllegalArgumentException {
        Dictionary dictionary = dictionaryBean.getDictionary();
        userService.addDictionary(user, dictionary);
        this.currentDictionary = dictionary;
    }

    @ValidationHandler @DialogValidation
    public String loadMediaItem() throws EJBIllegalArgumentException {
        MediaItem item = mediaItemBean.getMediaItem();

        byte[] content = null;
        try {
            if(mediaItemBean.getFile()!=null)
                content = IOUtils.toByteArray(mediaItemBean.getFile().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        item.setContent(content);
        dictionaryService.addMediaItem(currentDictionary, item);

        return "index?faces-redirect=true";
    }

    @ValidationHandler
    public void deleteMediaItems() throws EJBIllegalArgumentException {
        dictionaryService.removeMediaItems(currentDictionary, selectedMediaItems);
        selectedMediaItems = null;
    }

    @ValidationHandler @DialogValidation
    public void updateDictionary()throws EJBIllegalArgumentException{
        dictionaryService.update(currentDictionary);
        selectedMediaItems = null;
    }

    @ValidationHandler @DialogValidation
    public void removeDictionary()throws EJBIllegalArgumentException{
        userService.removeDictionary(user, currentDictionary);
        currentDictionary = null;
        selectedMediaItems = null;
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
        return currentDictionary!=null ? currentDictionary : new Dictionary();
    }


    public void setCurrentDictionary(Dictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
    }

    @Produces
    public List<Dictionary> getAllDictionaries(){
        return user != null ? user.getDictionaries() : null;
    }
}
