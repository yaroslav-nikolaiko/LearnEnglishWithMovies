package learn.english.core.entity;

import learn.english.parser.Parser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import learn.english.core.utils.Persistent;
import learn.english.core.utils.Category;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 6/6/14.
 */
@Entity
@Table(name = "WordCell")
@Data @EqualsAndHashCode(of = {"rootForm"}) @NoArgsConstructor()
public  class WordCell implements Persistent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rootForm;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ElementCollection()
    Set<String> words = new HashSet<>();

    public WordCell(String rootForm) {
        this.rootForm = rootForm;
    }

    public void addWord(String word) {
        words.add(word);
    }

    public void removeWord(String word) {
        words.remove(word);
    }

    public String getWord(){
        if(words.size()==0)
            return "";
        Iterator<String> iter = words.iterator();
        String result = iter.next();
        while(iter.hasNext()){
            String next = iter.next();
            result = next.length() < result.length() ? next : result;
        }
        return result;
    }


}
/*
@NamedQueries({@NamedQuery(name=WordCell.GET_ALL_WORDS_WHICH_HAVE_ITEM, query =
        "SELECT w FROM WordCell w WHERE ?1 MEMBER OF w.mediaItems")})
*/
