package yaroslav.learn.english.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.util.Persistent;
import yaroslav.learn.english.core.util.Category;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/6/14.
 */
@Entity
@Table(name = "WordCell")
@Data @EqualsAndHashCode(of = {"id"})
public  class WordCell implements Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(mappedBy = "words", cascade = CascadeType.ALL)
    private List<MediaItem> mediaItems;
    @ManyToOne
    private Dictionary dictionary;
    @Transient
    private String rootWord;

    public WordCell() {
    }

    public WordCell(String word) {
        this.word = word;
    }

}
