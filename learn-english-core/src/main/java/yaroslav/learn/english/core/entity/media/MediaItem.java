package yaroslav.learn.english.core.entity.media;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import yaroslav.learn.english.core.entity.Dictionary;
import yaroslav.learn.english.core.util.Persistent;
import yaroslav.learn.english.core.entity.WordCell;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Table(name = "MediaItem", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "DICTIONARY_ID"}))
@Data @ToString(of = {"name"}) @EqualsAndHashCode(of = {"name", "dictionary"})
public abstract class MediaItem implements Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] content;
    private String filename;
    @ManyToOne
    private Dictionary dictionary;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wordCell_mediaItem", joinColumns = @JoinColumn(name = "MEDIAITEM_ID"),
                                            inverseJoinColumns = @JoinColumn(name = "WORDCELL_ID"))
    private List<WordCell> words;

    public MediaItem() {
        words = new ArrayList<>();
    }

    public void addWords(Collection<String> words){
        for(String word : words){
            this.words.add(new WordCell(word));
        }
    }

    public abstract Map<String, String> getAttributes();

    public abstract void setAttributes(Map<String, String> attributes);

}
