package yaroslav.entity.media;

/**
 * Created by yaroslav on 6/2/14.
 */
public class Movie extends MediaItem {
    private String name;
    private String year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
