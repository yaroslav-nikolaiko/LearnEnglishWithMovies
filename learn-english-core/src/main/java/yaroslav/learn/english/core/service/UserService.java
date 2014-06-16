package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentsException;
import yaroslav.learn.english.core.interceptor.ValidationHandlerEjb;

import javax.ejb.ApplicationException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yaroslav on 6/8/14.
 */
@Stateless
@ValidationHandlerEjb
@ApplicationException(rollback = false)
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

    public User findByNameAndPassword(String name, String password){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name=:name AND u.password=:password", User.class);
        query.setParameter("name",name);
        query.setParameter("password",password);
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


    public void addDictionary(@NotNull User user,@NotNull Dictionary dictionary) throws EJBIllegalArgumentsException {
        String dName = dictionary.getName();
        for (Dictionary d : user.getDictionaries())
            if (d.getName().equals(dName))
                throw new EJBIllegalArgumentsException(String.format("Dictionary with name %s already exist", dName),
                                                                        EJBIllegalArgumentsException.MessageType.INFO);
        user.addDictionary(dictionary);
        //TODO: should I add validation user.id == getUserWithName(user.name).id ?
        em.persist(dictionary);
        //return em.merge(user);

//        if( ! em.contains(user))
//            em.merge(user);
//        else
//            em.persist(dictionary);
    }

//    public void merge(User user) {
//        em.merge(user);
//    }

//    public EntityManager getEntityManager() {
//        return em;
//    }

}
