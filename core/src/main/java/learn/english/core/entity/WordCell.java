package learn.english.core.entity;

import learn.english.parser.Parser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import learn.english.core.utils.Persistent;
import learn.english.core.utils.Category;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 6/6/14.
 */
@Entity
@Table(name = "WordCell")
@Data @EqualsAndHashCode(of = {"word"}) @NoArgsConstructor()
public  class WordCell implements Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Transient
    private String rootWord;

    public WordCell(String word) {
        this.word = word;
    }


}
/*
@NamedQueries({@NamedQuery(name=WordCell.GET_ALL_WORDS_WHICH_HAVE_ITEM, query =
        "SELECT w FROM WordCell w WHERE ?1 MEMBER OF w.mediaItems")})
*/
