package learn.english.model.adapter.provider;

import learn.english.model.entity.Dictionary;

/**
 * Created by yaroslav on 9/19/14.
 */
public interface DictionaryListenerProvider {
    void addTranslation(Dictionary dictionary);
}
