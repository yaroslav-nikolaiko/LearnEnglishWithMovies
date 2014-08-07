package learn.english.web.controller;

import lombok.Data;
import learn.english.core.entity.Dictionary;


import javax.annotation.PostConstruct;

import javax.enterprise.context.RequestScoped;

import javax.inject.Named;
import java.io.Serializable;


/**
 * Created by yaroslav on 6/12/14.
 */
@Named
@RequestScoped
public @Data class DictionaryBean implements Serializable {
    private Dictionary dictionary;

    @PostConstruct
    void init(){
        dictionary = new Dictionary();
    }

}
