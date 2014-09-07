package learn.english.core.utils;

import learn.english.core.service.*;
import learn.english.model.entity.MediaItem;
import learn.english.model.entity.wraper.MediaItems;
import learn.english.model.entity.wraper.WordCells;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yaroslav on 9/1/14.
 */
@ApplicationPath("rest")
public class ApplicationConfig extends Application {
    private final Set<Class<?>> classes;

    public ApplicationConfig() {
        HashSet<Class<?>> c = new HashSet<>();

        // ************************ REST Services ************************
        c.add(UserService.class);
        c.add(DictionaryService.class);
        c.add(GeneralProperties.class);
        c.add(MediaItemService.class);
        c.add(WordCellService.class);
        c.add(TranslatorFacade.class);
        c.add(WordCells.class);
        c.add(MediaItems.class);
        // *******************************(*******************************

        c.add(MOXyJsonProvider.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

}
