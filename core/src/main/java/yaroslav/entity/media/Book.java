package yaroslav.entity.media;

/**
 * Created by yaroslav on 6/2/14.
 */
public class Book extends MediaItem {
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
