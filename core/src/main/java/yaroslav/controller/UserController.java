package yaroslav.controller;


import yaroslav.entity.User;
import yaroslav.service.UserManager;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/7/14.
 */
@Named
@RequestScoped
public class UserController implements Serializable {


    @PersistenceContext( unitName = "LearnEnglishWithMovies")
    private EntityManager em;

    private User user;
    @EJB
    private UserManager manager;

//    @PostConstruct
//    void init(){
//        hiberinit();
//    }

//    @Transactional
//    private void hiberinit(){
//        Query query = em.createQuery("SELECT u FROM User u WHERE u.name='yaroslav' ");
//        user = (User) query.getSingleResult();
//    }


    public User getUser(){
        return manager.getUser();
    }

//    public void insertData(){
//
//    }


}
