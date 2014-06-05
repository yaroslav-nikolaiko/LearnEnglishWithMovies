package yaroslav.entity.media;

import yaroslav.entity.Dictionary;
import yaroslav.entity.DictionaryUnit;
import yaroslav.entity.Word;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
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
    @Transient
    private List<DictionaryUnit> words;

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

    public List<DictionaryUnit> getWords() {
        return words;
    }

    public void setWords(List<DictionaryUnit> words) {
        this.words = words;
    }
}
