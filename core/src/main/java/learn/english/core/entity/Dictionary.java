package learn.english.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import learn.english.core.utils.Persistent;
import learn.english.core.utils.Language;
import learn.english.core.utils.Level;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Table(name = "Dictionary", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "USER_FK"}))
@Data @ToString(of = {"name"}) @EqualsAndHashCode(of = {"name", "learningLanguage", "nativeLanguage", "level"})
public class Dictionary implements Persistent {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionary", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MediaItem> mediaItems;

    public Dictionary() {
        mediaItems = new ArrayList<>();
    }

    public void addMediaItem(MediaItem item) {
        item.setDictionary(this);
        mediaItems.add(item);
    }

    public void removeMediaItems(Collection<MediaItem> items){
        mediaItems.removeAll(items);
    }

    public void removeMediaItem(MediaItem item){
        mediaItems.remove(item);
        item.destructor();
        item.setDictionary(null);
    }


    public void update(Dictionary managedDictionary) {
        this.name = managedDictionary.name;
        this.learningLanguage = managedDictionary.learningLanguage;
        this.nativeLanguage = managedDictionary.nativeLanguage;
        this.level = managedDictionary.level;
        this.mediaItems = managedDictionary.mediaItems;
    }
}
