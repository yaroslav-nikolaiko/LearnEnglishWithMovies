package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.util.Language;
import yaroslav.learn.english.core.util.Level;

import javax.ejb.Singleton;
import javax.inject.Named;

/**
 * Created by yaroslav on 6/12/14.
 */
@Singleton
@Named
public class GeneralProperties {

    public  Language[] getAvailableLearningLanguages() {
        return Language.values();
    }

    public  Language[] getAvailableNativeLanguages(){
        return Language.values();
    }

    public Level[] getAvailableLevels(){
        return Level.values();
    }


}
