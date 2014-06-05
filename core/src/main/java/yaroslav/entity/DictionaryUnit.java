package yaroslav.entity;

import yaroslav.util.Category;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/5/14.
 */
@Entity
public class DictionaryUnit {
    @Id
    @GeneratedValue
    private Long id;
    private Word word;
    private Integer count;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "dictionary_fk")
    private Dictionary dictionary;
    @Transient
    private List<String> translation;
    @Transient
    private List<String> rootTranslation;
    @Transient
    private String definition;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
