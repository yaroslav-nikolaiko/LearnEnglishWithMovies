package learn.english.model.entity;

import learn.english.model.utils.Category;
import learn.english.model.utils.Persistent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

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
    @Transient
    Map<String,String> translation = new HashMap<>();

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
