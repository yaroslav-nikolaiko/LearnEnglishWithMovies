package learn.english.model.adapter;

import learn.english.model.adapter.provider.DictionaryListenerProvider;
import learn.english.model.entity.Dictionary;

import javax.inject.Inject;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 * Created by yaroslav on 9/19/14.
 */
public class DictionaryListener {
    @Inject DictionaryListenerProvider provider;
    @PostPersist
    @PostLoad
    public void addTranslation(Dictionary dictionary) {
        provider.addTranslation(dictionary);
    }
}
