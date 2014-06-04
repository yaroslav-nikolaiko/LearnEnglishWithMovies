package yaroslav.entity;

import yaroslav.entity.media.MediaItem;
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
    private String learningLanguage;
    private String nativeLanguage;
    @Enumerated(EnumType.STRING)
    private Level level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionary")
    private List<Word> words;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionary")
    private List<MediaItem> mediaItems;

    public Dictionary() {
        words = new ArrayList<>();
        mediaItems = new ArrayList<>();
    }

    public void addWords(Collection<String> words){
        for(String word : words)
            this.words.add(new Word(word));
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

    public String getLearningLanguage() {
        return learningLanguage;
    }

    public void setLearningLanguage(String learningLanguage) {
        this.learningLanguage = learningLanguage;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }
}
