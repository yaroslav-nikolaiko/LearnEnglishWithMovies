package yaroslav.learn.english.core.entity.media;

import lombok.Data;
import yaroslav.learn.english.core.entity.MediaItem;

import javax.persistence.Entity;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public @Data class Book extends MediaItem {
    String author;
}
