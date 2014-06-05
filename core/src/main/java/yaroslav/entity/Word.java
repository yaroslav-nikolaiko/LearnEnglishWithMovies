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

}
