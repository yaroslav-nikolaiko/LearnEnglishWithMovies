package yaroslav.entity;

import yaroslav.util.Category;

import javax.persistence.*;

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
    private Integer count;
    @Enumerated(EnumType.STRING)
    private Category category;
}
