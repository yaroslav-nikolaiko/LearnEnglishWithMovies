package yaroslav.entity;

import yaroslav.entity.media.MediaItem;
import yaroslav.util.Category;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/6/14.
 */
@Entity
public class DictionaryUnit {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;
    @Transient
    private List<MediaItem> mediaItems;
}
