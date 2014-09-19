package learn.english.model.entity;


import learn.english.model.adapter.DictionaryListener;
import learn.english.model.adapter.MediaItemAdapter;
import learn.english.model.utils.Language;
import learn.english.model.utils.Level;
import learn.english.model.utils.Persistent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Table(name = "Dictionary", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "USER_FK"}))
@NamedQueries({@NamedQuery(name=Dictionary.FIND_BY_MEDIA_ITEM, query = "SELECT d from Dictionary d WHERE ?1 MEMBER OF d.mediaItems")})
@Data @ToString(of = {"name"}) @EqualsAndHashCode(of = {"name", "learningLanguage", "nativeLanguage", "level"})
@EntityListeners({DictionaryListener.class})
public class Dictionary implements Persistent {
    public static final String FIND_BY_MEDIA_ITEM = "FIND_BY_MEDIA_ITEM";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max=20)
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language learningLanguage;
    @Enumerated(EnumType.STRING)
    private Language nativeLanguage;
    @Enumerated(EnumType.STRING)
    private Level level;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "DICTIONARY_ID")
    @XmlJavaTypeAdapter(MediaItemAdapter.class)
    private List<MediaItem> mediaItems;

    public Dictionary() {
        mediaItems = new ArrayList<>();
    }

    public void addMediaItem(MediaItem item) {
        mediaItems.add(item);
    }

    public void removeMediaItems(Collection<MediaItem> items){
        mediaItems.removeAll(items);
    }

    public void removeMediaItem(MediaItem item){
        mediaItems.remove(item);
    }


    public void update(Dictionary managedDictionary) {
        this.name = managedDictionary.name;
        this.learningLanguage = managedDictionary.learningLanguage;
        this.nativeLanguage = managedDictionary.nativeLanguage;
        this.level = managedDictionary.level;
        this.mediaItems = managedDictionary.mediaItems;
    }
}
