package yaroslav.learn.english.core.entity.media;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public @Data class Movie extends MediaItem {
    String year;
}
