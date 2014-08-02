package yaroslav.learn.english.core.entity.media;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public @Data class TVShow extends MediaItem {
    String season;
    String episode;
}
