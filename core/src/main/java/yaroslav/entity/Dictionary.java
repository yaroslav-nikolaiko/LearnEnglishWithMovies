package yaroslav.entity;

import yaroslav.entity.media.MediaItem;
import yaroslav.util.Language;
import yaroslav.util.Level;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public class Dictionary {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language learningLanguage;
    @Enumerated(EnumType.STRING)
    private Language nativeLanguage;
    @Enumerated(EnumType.STRING)
    private Level level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionary")
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("learningLanguage  " + learningLanguage ).append("\n");
        result.append("nativeLanguage  " + nativeLanguage ).append("\n");
        result.append("level  " + level ).append("\n");
        for(MediaItem item : mediaItems){
            result.append("Item : "+"\n" + item);
        }
        return result.toString();
    }
}
