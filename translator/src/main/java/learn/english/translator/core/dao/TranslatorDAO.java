package learn.english.translator.core.dao;

import java.io.Serializable;

/**
 * Created by yaroslav on 9/22/14.
 */
public interface TranslatorDAO extends Serializable {
    boolean contains(String text);
    void save(String text, String translation);
    String translation(String text);
    String getDBName();
    default void onDestroy(){/*NOP*/}
}
