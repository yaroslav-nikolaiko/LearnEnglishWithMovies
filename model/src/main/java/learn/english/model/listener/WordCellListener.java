package learn.english.model.listener;

import learn.english.model.listener.provider.EntitiesListenerProvider;
import learn.english.model.entity.WordCell;

import javax.inject.Inject;
import javax.persistence.PostLoad;

/**
 * Created by yaroslav on 9/20/14.
 */
public class WordCellListener {
    @Inject
    EntitiesListenerProvider provider;

    @PostLoad
    public void addTranslation(WordCell cell) {
        if(cell.getTranslation().isEmpty())
            provider.addTranslation(cell);
    }
}
