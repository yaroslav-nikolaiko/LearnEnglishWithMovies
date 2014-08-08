package learn.english.core.entity;

import learn.english.parser.Parser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import learn.english.core.utils.Persistent;
import learn.english.core.utils.Category;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/6/14.
 */
@Entity
@Table(name = "WordCell")
@Data @EqualsAndHashCode(of = {"word"}) @NoArgsConstructor()
public  class WordCell implements Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(mappedBy = "words")
    private List<MediaItem> mediaItems;
    /*@ManyToOne
    private Dictionary dictionary;*/
    @Transient
    private String rootWord;

    public WordCell(String word) {
        this.word = word;
    }

    public void removeMediaItem(MediaItem item) {
        mediaItems.remove(item);
    }

/*    public WordCell(String word, Dictionary dictionary){
        this.word = word;
        this.dictionary = dictionary;
    }*/

}
