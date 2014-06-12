package yaroslav.learn.english.web.controller;

import yaroslav.learn.english.core.service.UserService;
import yaroslav.learn.english.core.util.Language;
import yaroslav.learn.english.core.util.Level;


import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;


/**
 * Created by yaroslav on 6/12/14.
 */
@Named
@RequestScoped
public class DictionaryController implements Serializable {
    @EJB
    private UserService userService;

    private Language learningLanguage;
    private Language nativeLanguage;
    private Level level;
    private String name;


    public Language[] getAvailableLearningLanguages() {
        return Language.values();
    }

    public Language[] getAvailableNativeLanguages(){
        return Language.values();
    }

    public Level[] getAvailableLevels(){
        return Level.values();
    }

    public Language getLearningLanguage() {
        return learningLanguage;
    }

    public void setLearningLanguage(Language learningLanguage) {
        this.learningLanguage = learningLanguage;
    }

    public Language getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(Language nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public Level getLevel() {
         return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
