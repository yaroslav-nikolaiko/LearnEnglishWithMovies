package yaroslav.learn.english.core.entity;

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
               @NamedQuery(name=User.COUNT_BY_EMAIL, query = "SELECT COUNT(u.email) FROM User u WHERE u.email=?1")})
public class User {
    public static final String FIND_BY_NAME_AND_PASSWORD = "User.findByLoginAndPassword";
    public static final String COUNT_BY_NAME = "User.countByLogin";
    public static final String COUNT_BY_EMAIL = "User.countByEmail";
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public String toString() {
//        StringBuilder result = new StringBuilder();
//        result.append("name  " + name ).append("\n");
//        result.append("email  " + email ).append("\n");
//        for(Dictionary dict : dictionaries){
//            result.append("Dictionary : "+"\n" + dict);
//        }
//        return result.toString();
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
