package yaroslav.entity;

import yaroslav.util.Category;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public class Word {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String word;
    private String rootWord;
    @ElementCollection
    private List<String> translation;
    private Integer count;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "dictionary_fk")
    private Dictionary dictionary;

    public Word(){

    }

    public Word(String word){
        this.word = word;
    }

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

    public String getRootWord() {
        return rootWord;
    }

    public void setRootWord(String rootWord) {
        this.rootWord = rootWord;
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
}
