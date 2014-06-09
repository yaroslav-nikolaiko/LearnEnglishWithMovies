package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Created by yaroslav on 6/8/14.
 */
@Stateless
public class UserService {
    @PersistenceContext( unitName = "LearnEnglishWithMovies")
    private EntityManager em;


    public void add(User user){
        em.persist(user);
    }

    public boolean nameExist(String name){
        Query query = em.createQuery("SELECT COUNT(u.name) FROM User u WHERE u.name=:name");
        query.setParameter("name",name);
        return (Long) query.getSingleResult() > 0;
    }

    public boolean emailExist(String email){
        Query query = em.createQuery("SELECT COUNT(u.email) FROM User u WHERE u.email=:email");
        query.setParameter("email",email);
        return (Long) query.getSingleResult() > 0;
    }
}
