package learn.english.model.entity.wraper;

import learn.english.model.entity.MediaItem;
import lombok.Data;

import java.util.Collection;

/**
 * Created by yaroslav on 9/5/14.
 */
public @Data class MediaItems {
    Collection<MediaItem> items;
}
