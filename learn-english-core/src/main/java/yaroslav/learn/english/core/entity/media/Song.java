package yaroslav.learn.english.core.entity.media;

import javax.persistence.Entity;
import java.util.Map;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public class Song extends MediaItem {
    private String author;
    private String album;
    private String year;

    @Override
    public Map<String, String> getAttributes() {
        return null;
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {

    }
}
