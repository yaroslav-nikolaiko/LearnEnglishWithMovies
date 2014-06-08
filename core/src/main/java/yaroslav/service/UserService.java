package yaroslav.service;

import yaroslav.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by yaroslav on 6/8/14.
 */
public class UserService {
    @PersistenceContext( unitName = "LearnEnglishWithMovies")
    private EntityManager em;

    public void add(User user){
        em.persist(user);
    }

//    public boolean unique(User user){
//        em.merge()
//    }
}
