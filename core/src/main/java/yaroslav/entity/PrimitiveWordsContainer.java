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
    @CollectionTable(name = "primitives")
    @MapKeyColumn(name = "language")
    @Column(name = "word")
    private Map<String, String> words;
}


