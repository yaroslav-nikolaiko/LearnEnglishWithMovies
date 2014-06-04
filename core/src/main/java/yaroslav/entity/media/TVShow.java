package yaroslav.entity.media;

import javax.persistence.Entity;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public class TVShow extends MediaItem {
    private String name;
    private String season;
    private String episode;
}
