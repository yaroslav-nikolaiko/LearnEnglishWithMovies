package learn.english.core.test;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by yaroslav on 12/21/14.
 */
@Stateless
public class DataBaseService {
    @Inject
    EntityManager em;

    public void executeNativeQuery(String query) {
        Query nativeQuery = em.createNativeQuery(query);
        nativeQuery.executeUpdate();
    }

    public List executeNativeQuery2(String query){
        Query nativeQuery = em.createNativeQuery(query, String.class);
        return nativeQuery.getResultList();
    }
}
