package learn.english.core.entity.media;

import learn.english.core.entity.MediaItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Data @EqualsAndHashCode(callSuper = true)
public class Song extends MediaItem {
    String author;
    String album;
    String year;
}
