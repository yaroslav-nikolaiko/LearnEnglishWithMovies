package yaroslav.service;

import yaroslav.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/7/14.
 */
@Stateless
public class UserManager implements Serializable {
    @PersistenceContext( unitName = "LearnEnglishWithMovies")
    private EntityManager em;

    public User getUser(){
        Query query = em.createQuery("SELECT u FROM User u WHERE u.name='yaroslav' ");
        return (User) query.getSingleResult();
    }
}
