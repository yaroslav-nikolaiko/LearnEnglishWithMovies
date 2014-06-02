package yaroslav.entity;

import yaroslav.util.Level;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/1/14.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Level level;
    private List<Dictionary> dictionaries;
    private String nativeLanguage;




    public Long getId() {
        return id;
    }

}
