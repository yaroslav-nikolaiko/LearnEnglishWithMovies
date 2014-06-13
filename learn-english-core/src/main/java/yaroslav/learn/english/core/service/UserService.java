package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/8/14.
 */
@Stateless
public class UserService {
    @Inject
    private EntityManager em;


    public void add(User user){
        em.persist(user);
    }

    public User findByName(String name){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name=:name", User.class);
        query.setParameter("name",name);
        List<User> list = query.getResultList();
        if(list.size() != 1)
            return null;
        return list.get(0);
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

    public void addDictionary(User user, Dictionary dictionary){
        if( user != null && dictionary != null){
            user.addDictionary(dictionary);
            //TODO: should I add validation user.id == getUserWithName(user.name).id ?
            merge(user);
        }
    }

    public void merge(User user) {
        em.merge(user);
    }
}
