package yaroslav.learn.english.core.entity.media;

import lombok.Data;
import yaroslav.learn.english.core.entity.MediaItem;

import javax.persistence.*;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public @Data class Movie extends MediaItem {
    String year;
}
