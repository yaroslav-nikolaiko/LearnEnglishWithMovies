package learn.english.core.service;

import learn.english.core.validation.ExistInDB;
import learn.english.core.entity.Dictionary;
import learn.english.core.entity.User;
import learn.english.core.exception.EJBIllegalArgumentException;
import learn.english.core.validation.ValidationHandlerEjb;
import learn.english.utils.Loggable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;

/**
 * Created by yaroslav on 6/8/14.
 */
@Stateless
@ValidationHandlerEjb @Loggable
@ApplicationException(rollback = false)
public class UserService extends AbstractService<User> {
    @EJB DictionaryService dictionaryService;

    @Inject
    public UserService(EntityManager em) {
        super(User.class);
        this.em = em;
    }

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


    public void addDictionary(@ExistInDB User user, Dictionary dictionary) throws EJBIllegalArgumentException {
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

    public void removeDictionary(@ExistInDB User user, Dictionary dictionary){
        user.removeDictionary(dictionary);
        dictionaryService.garbageCollector(dictionary);
        update(user);
    }
}
