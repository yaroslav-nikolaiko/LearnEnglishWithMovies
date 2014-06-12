package yaroslav.learn.english.core.entity;

import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.util.Language;
import yaroslav.learn.english.core.util.Level;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Table(name = "Dictionary")
public class Dictionary {
    @Id
    @GeneratedValue
    private Long id;
    @Size(max=20)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language learningLanguage;
    @Enumerated(EnumType.STRING)
    private Language nativeLanguage;
    @Enumerated(EnumType.STRING)
    private Level level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionary", fetch = FetchType.LAZY)
    private List<MediaItem> mediaItems;

    public Dictionary() {
        mediaItems = new ArrayList<>();
    }

    public void addMediaItem(MediaItem item) {
        mediaItems.add(item);
    }

    /********************************************************************************************
     *                                              Getters and Setters
     ********************************************************************************************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLearningLanguage() {
        return learningLanguage;
    }

    public void setLearningLanguage(Language learningLanguage) {
        this.learningLanguage = learningLanguage;
    }

    public Language getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(Language nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("learningLanguage  ").append(learningLanguage).append("\n");
        result.append("nativeLanguage  ").append(nativeLanguage).append("\n");
        result.append("level  ").append(level).append("\n");
        for(MediaItem item : mediaItems){
            result.append("Item : " + "\n").append(item);
        }
        return result.toString();
    }
}
