package learn.english.core.entity.media;

import learn.english.core.entity.MediaItem;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public @Data class Song extends MediaItem {
    String author;
    String album;
    String year;
}
