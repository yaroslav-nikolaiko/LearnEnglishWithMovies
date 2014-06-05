package yaroslav.entity.media;

/**
 * Created by yaroslav on 6/2/14.
 */
public class Song extends MediaItem {
    private String author;
    private String album;
    private String year;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
