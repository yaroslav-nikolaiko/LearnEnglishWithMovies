package learn.english.web.controller;

import learn.english.core.logger.message.DictionaryMessage;
import learn.english.utils.ConfigurationManager;
import learn.english.utils.Loggable;
import learn.english.core.logger.message.UserMessage;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import learn.english.core.entity.Dictionary;
import learn.english.core.entity.User;
import learn.english.core.entity.MediaItem;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.service.DictionaryService;
import learn.english.core.service.MediaItemService;
import learn.english.core.service.UserService;
import learn.english.web.validation.DialogValidation;
import learn.english.web.validation.ValidationHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    final static Logger logger = LogManager.getLogger(ConfigurationManager.value("logger"));
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

    @Loggable
    public String singUp(){
        user =  userBean.getUser();
        userService.addToDataBase(user);
        logger.info(UserMessage.signUp(user));
        return "index?faces-redirect=true";
    }

    @DialogValidation @Loggable
    public void login(){
        init();
        user =  userBean.login();
        if( user!=null && user.getDictionaries().size() > 0)
            currentDictionary = user.getDictionaries().get(0);
        logger.info(UserMessage.logIn(user));
    }


    @DialogValidation @Loggable
    public void createDictionary() throws EJBIllegalArgumentException {
        Dictionary dictionary = dictionaryBean.getDictionary();
        userService.addDictionary(user, dictionary);
        this.currentDictionary = dictionary;
        logger.debug(DictionaryMessage.create(dictionary));
    }

    @DialogValidation @Loggable
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

    @Loggable
    public void deleteMediaItems() throws EJBIllegalArgumentException {
        dictionaryService.removeMediaItems(currentDictionary, selectedMediaItems);
        selectedMediaItems = null;
    }

    @DialogValidation @Loggable
    public void updateDictionary() throws EJBIllegalArgumentException{
        System.out.println("Update Dictionary in DB");
        dictionaryService.update(currentDictionary);
        selectedMediaItems = null;
        logger.debug(DictionaryMessage.update(currentDictionary));
    }

    @DialogValidation @Loggable
    public void removeDictionary() throws EJBIllegalArgumentException{
        userService.removeDictionary(user, currentDictionary);
        logger.debug(DictionaryMessage.remove(currentDictionary));
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
