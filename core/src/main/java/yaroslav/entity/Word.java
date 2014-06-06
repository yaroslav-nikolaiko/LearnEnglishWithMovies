package yaroslav.entity;

import yaroslav.entity.media.MediaItem;
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
    @Column(nullable = false)
    private String language;
    private String rootWord;



    public Word(){

    }

    public Word(String word, String language){
        this.word = word;
    }



}
