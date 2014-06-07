package yaroslav.controller;


import yaroslav.entity.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/7/14.
 */
@Named
@SessionScoped
public class UserController implements Serializable {
    @PersistenceContext( name = "LearnEnglishWithMovies")
    private EntityManager em;

    private User user;

    @PostConstruct
    void init(){
        user = em.find(User.class, 1);
    }

    public User getUser(){
        return user;
    }

}
