package learn.english.translator.core.dao.factory;

import learn.english.translator.core.dao.TranslatorDAO;

import java.io.Serializable;

/**
 * Created by yaroslav on 9/22/14.
 */
public interface TranslatorDAOFactory extends Serializable {
    TranslatorDAO getInstance(String DBName);
    default void onLoad(){/*NOP*/}
    default void onDestroy(){/*NOP*/}
}
