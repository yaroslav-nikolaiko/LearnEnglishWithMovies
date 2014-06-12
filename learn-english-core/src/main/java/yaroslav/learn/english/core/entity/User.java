package yaroslav.learn.english.core.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yaroslav on 6/1/14.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk")
    private List<Dictionary> dictionaries;

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
        StringBuilder result = new StringBuilder();
        result.append("name  " + name ).append("\n");
        result.append("email  " + email ).append("\n");
        for(Dictionary dict : dictionaries){
            result.append("Dictionary : "+"\n" + dict);
        }
        return result.toString();
    }
}
