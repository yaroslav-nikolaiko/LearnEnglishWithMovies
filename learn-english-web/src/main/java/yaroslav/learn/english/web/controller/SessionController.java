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

    public String singUp(){
        user =  userBean.getUser();
        userService.add(user);
        return "index?faces-redirect=true";
    }

    @ValidationHandler @DialogValidation
    public void login(){
        user =  userBean.login();
        if( user!=null && user.getDictionaries().size() > 0)
            currentDictionary = user.getDictionaries().get(0);
    }

    @ValidationHandler
    @DialogValidation
    public void createDictionary() throws EJBIllegalArgumentException {
        Dictionary dictionary = dictionaryBean.getDictionary();
        //user = userService.addDictionary(user, dictionary);
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
        //currentDictionary = dictionaryService.addMediaItem(currentDictionary, item);
        dictionaryService.addMediaItem(currentDictionary, item);

        return "index?faces-redirect=true";
    }

    @ValidationHandler
    public void deleteMediaItems() throws EJBIllegalArgumentException {
        dictionaryService.removeMediaItems(currentDictionary, selectedMediaItems);
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
