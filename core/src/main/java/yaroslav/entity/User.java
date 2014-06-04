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
    @OneToMany
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
}
