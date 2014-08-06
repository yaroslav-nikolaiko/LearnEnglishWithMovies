package learn.english.web.controller;

import lombok.Data;
import org.apache.commons.io.IOUtils;
import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.entity.MediaItem;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentException;
import yaroslav.learn.english.core.service.DictionaryService;
import yaroslav.learn.english.core.service.MediaItemService;
import yaroslav.learn.english.core.service.UserService;
import learn.english.web.interceptor.DialogValidation;
import learn.english.web.interceptor.ValidationHandler;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by yaroslav on 6/9/14.
 */
@Named
@SessionScoped
@ValidationHandler
public @Data class SessionController implements Serializable {
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

    @DialogValidation
    public void login(){
        init();
        user =  userBean.login();
        if( user!=null && user.getDictionaries().size() > 0)
            currentDictionary = user.getDictionaries().get(0);
    }


    @DialogValidation
    public void createDictionary() throws EJBIllegalArgumentException {
        Dictionary dictionary = dictionaryBean.getDictionary();
        userService.addDictionary(user, dictionary);
        this.currentDictionary = dictionary;
    }

    @DialogValidation
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


    public void deleteMediaItems() throws EJBIllegalArgumentException {
        dictionaryService.removeMediaItems(currentDictionary, selectedMediaItems);
        selectedMediaItems = null;
    }

    @DialogValidation
    public void updateDictionary()throws EJBIllegalArgumentException{
        dictionaryService.update(currentDictionary);
        selectedMediaItems = null;
    }

    @DialogValidation
    public void removeDictionary()throws EJBIllegalArgumentException{
        userService.removeDictionary(user, currentDictionary);
        currentDictionary = null;
        selectedMediaItems = null;
    }


    public Dictionary getCurrentDictionary() {
        return currentDictionary!=null ? currentDictionary : new Dictionary();
    }

    @Produces
    public List<Dictionary> getAllDictionaries(){
        return user != null ? user.getDictionaries() : null;
    }
}
