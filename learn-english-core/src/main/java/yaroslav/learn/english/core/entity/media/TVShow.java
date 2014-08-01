package yaroslav.learn.english.core.entity.media;

import javax.persistence.Entity;
import java.util.Map;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public class TVShow extends MediaItem {
    private String season;
    private String episode;

    @Override
    public Map<String, String> getAttributes() {
        return null;
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {

    }
}
