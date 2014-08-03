package yaroslav.learn.english.core.service;

import yaroslav.learn.english.core.utils.Language;
import yaroslav.learn.english.core.utils.Level;
import yaroslav.learn.english.core.utils.MediaItemType;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 * Created by yaroslav on 6/12/14.
 */
@Singleton
@Named
@Lock(LockType.READ)
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

    public MediaItemType[] getAvailableMediaItemTypes() {
        return MediaItemType.values();
    }
}
