package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.entity.User;
import yaroslav.learn.english.core.exception.EJBIllegalArgumentException;
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
public class UserService extends AbstractService<User> {

    @Inject
    public UserService(EntityManager em) {
        super(User.class);
        this.em = em;
    }

/*    public User findByName(String name){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name=:name", User.class);
        query.setParameter("name",name);
        List<User> list = query.getResultList();
        if(list.size() != 1)
            return null;
        return list.get(0);
    }*/

    public User findByNameAndPassword(String name, String password) {
        return singeResult(User.FIND_BY_NAME_AND_PASSWORD, name, password);
    }

    public boolean nameExist(String name){
        Query query = buildSimpleQuery(User.COUNT_BY_NAME, name);
        return (Long) query.getSingleResult() > 0;
    }

    public boolean emailExist(String email){
        Query query = buildSimpleQuery(User.COUNT_BY_EMAIL, email);
        return (Long) query.getSingleResult() > 0;
    }


    public void addDictionary(@NotNull User user,@NotNull Dictionary dictionary) throws EJBIllegalArgumentException {
        String dName = dictionary.getName();
        for (Dictionary d : user.getDictionaries())
            if (d.getName().equals(dName))
                throw new EJBIllegalArgumentException(String.format("Dictionary with name %s already exist", dName),
                                                                        EJBIllegalArgumentException.MessageType.INFO);
        user.addDictionary(dictionary);
        //TODO: should I add validation user.id == getUserWithName(user.name).id ?
        em.persist(dictionary);
        em.merge(user);
        //return em.merge(user);
    }

    public void removeDictionary(@NotNull User user,@NotNull Dictionary dictionary){
        user.removeDictionary(dictionary);
        update(user);
    }
}
