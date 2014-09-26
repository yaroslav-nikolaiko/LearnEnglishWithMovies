package learn.english.model.entity;


import learn.english.model.utils.Persistent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaroslav on 6/1/14.
 */
@Entity
@Table(name = "User")
@NamedQueries({@NamedQuery(name=User.FIND_BY_NAME_AND_PASSWORD, query = "SELECT u from User u WHERE u.name=?1 AND u.password=?2"),
               @NamedQuery(name=User.COUNT_BY_NAME, query = "SELECT COUNT(u.name) FROM User u WHERE u.name=?1"),
               @NamedQuery(name=User.COUNT_BY_EMAIL, query = "SELECT COUNT(u.email) FROM User u WHERE u.email=?1"),
               @NamedQuery(name=User.FIND_BY_DICTIONARY, query = "SELECT u from User u WHERE ?1 MEMBER OF u.dictionaries"),
               @NamedQuery(name=User.FIND_BY_NAME, query = "SELECT u from User u WHERE u.name=?1")})
@Data @ToString(of = {"name"}) @EqualsAndHashCode(of = {"name"})
public class User implements Persistent {
    public static final String FIND_BY_NAME_AND_PASSWORD = "User.findByLoginAndPassword";
    public static final String FIND_BY_NAME = "User.FIND_BY_NAME";
    public static final String COUNT_BY_NAME = "User.countByLogin";
    public static final String COUNT_BY_EMAIL = "User.countByEmail";
    public static final String FIND_BY_DICTIONARY = "User.FIND_BY_DICTIONARY";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=20)
    @Column(nullable = false, unique = true)
    private String name;

    @Size(min=3, max=20)
    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Email is not in valid format")
    @Column(unique = true)
    private String email;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "USER_FK")
    private List<Dictionary> dictionaries;

    public User() {
        dictionaries = new ArrayList<>();
    }

    public void addDictionary(Dictionary dictionary){
        dictionaries.add(dictionary);
    }

    public void removeDictionary(Dictionary dictionary) {
        dictionaries.remove(dictionary);
    }
}
