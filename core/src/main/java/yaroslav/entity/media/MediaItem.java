package yaroslav.entity.media;

import yaroslav.entity.Dictionary;
import yaroslav.entity.WordCell;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Table(name = "MediaItem")
public class MediaItem {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] content;
    private String filename;
    @ManyToOne
    private Dictionary dictionary;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wordCell_mediaItem", joinColumns = @JoinColumn(name = "mediaItem_id"),
                                            inverseJoinColumns = @JoinColumn(name = "wordCell_fk"))
    private List<WordCell> words;

    public MediaItem() {
        words = new ArrayList<>();
    }

    public void addWords(Collection<String> words){
        for(String word : words){
            this.words.add(new WordCell(word));
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public List<WordCell> getWords() {
        return words;
    }

    public void setWords(List<WordCell> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("name  " + name ).append("\n");
        result.append("dictionary  " + dictionary.getId() ).append("\n");
        result.append("dictionary  " + dictionary ).append("\n");
        for(WordCell word : words){
            result.append("Item : "+"\n" + word);
        }
        return result.toString();
    }
}
