package yaroslav.entity;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
public class PrimitiveWordsContainer {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    @CollectionTable(name = "Primitives")
    @MapKeyColumn(name = "language")
    @Column(name = "word")
    private Map<String, String> words;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getWords() {
        return words;
    }

    public void setWords(Map<String, String> words) {
        this.words = words;
    }
}


