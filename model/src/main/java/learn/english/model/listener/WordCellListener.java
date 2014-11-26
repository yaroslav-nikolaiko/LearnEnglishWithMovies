package learn.english.model.listener;

import learn.english.model.listener.provider.EntitiesListenerProvider;
import learn.english.model.entity.WordCell;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PostLoad;

/**
 * Created by yaroslav on 9/20/14.
 */
public class WordCellListener {
    //@Inject
    //@Resource(lookup = "java:global/lingvo-movie-core/EntitiesListener")
    EntitiesListenerProvider provider;

    @PostLoad
    public void addTranslation(WordCell cell) {
        try {
            InitialContext ic = new InitialContext();
            provider = (EntitiesListenerProvider)ic.lookup("java:global/lingvo-movie-core/EntitiesListener");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        if(cell.getTranslation().isEmpty())
            provider.addTranslation(cell);
    }
}
