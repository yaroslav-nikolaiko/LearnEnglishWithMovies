package yaroslav.learn.english.core.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by yaroslav on 6/12/14.
 */
@Stateless
public class DictionaryService {
    @Inject
    EntityManager em;


}
