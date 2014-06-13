package yaroslav.learn.english.core.entity;

import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.util.Category;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/6/14.
 */
@Entity
@Table(name = "WordCell")
public class WordCell {
    @Id
    @GeneratedValue
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

    /********************************************************************************************
     *                                              Getters and Setters
     ********************************************************************************************/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public String getRootWord() {
        return rootWord;
    }

    public void setRootWord(String rootWord) {
        this.rootWord = rootWord;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("word  " + word ).append("\n");
        result.append("category  " + category ).append("\n");
        result.append("rootWord  " + rootWord ).append("\n");
        for(MediaItem item : mediaItems){
            result.append("mediaItems : "+"\n" + item.getId());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordCell wordCell = (WordCell) o;

        if (id != null ? !id.equals(wordCell.id) : wordCell.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
