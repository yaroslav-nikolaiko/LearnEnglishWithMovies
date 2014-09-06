package learn.english.web.rest;

import learn.english.model.entity.MediaItem;
import learn.english.model.entity.WordCell;
import learn.english.model.utils.MediaItemType;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.core.GenericEntity;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by yaroslav on 9/6/14.
 */
public class MediaItemService implements Serializable{
    @Inject RestService restService;

    public MediaItem generateItem(MediaItemType type) {
        return restService.path("item/type").path(type.toString()).get(MediaItem.class);
    }

    public Set<WordCell> getUniqueWords(MediaItem mediaItem) {
        return (Set<WordCell>) restService.path("wordcell/unique").path(String.valueOf(mediaItem.getId())).get(GenericEntity.class).getEntity();
    }
}
