package learn.english.model.listener.provider;

import learn.english.model.entity.Dictionary;
import learn.english.model.entity.WordCell;

/**
 * Created by yaroslav on 9/19/14.
 */
public interface EntitiesListenerProvider {
    void addTranslation(Dictionary dictionary);

    void addTranslation(WordCell cell);
}
