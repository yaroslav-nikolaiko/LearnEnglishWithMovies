package yaroslav.learn.english.web.controller;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.service.UserService;
import yaroslav.learn.english.core.util.Language;
import yaroslav.learn.english.core.util.Level;


import javax.annotation.PostConstruct;
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
public class DictionaryBean implements Serializable {
    private Dictionary dictionary;

    @PostConstruct
    void init(){
        dictionary = new Dictionary();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

}
