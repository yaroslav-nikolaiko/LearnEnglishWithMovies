package yaroslav.learn.english.web;


import java.io.Serializable;

/**
 * Created by yaroslav on 6/14/14.
 */
public class Theme implements Serializable {

    private int id;
    private String displayName;
    private String name;

    public Theme() {}

    public Theme(int id, String displayName, String name) {
        this.id = id;
        this.displayName = displayName;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theme theme = (Theme) o;

        if (id != theme.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}