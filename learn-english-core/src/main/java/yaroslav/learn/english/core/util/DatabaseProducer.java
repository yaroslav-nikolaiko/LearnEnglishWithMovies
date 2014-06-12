package yaroslav.learn.english.core.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by yaroslav on 6/12/14.
 */
public class DatabaseProducer {
    @Produces
    @PersistenceContext(unitName = "LearnEnglishWithMovies")
    private EntityManager em;
}
