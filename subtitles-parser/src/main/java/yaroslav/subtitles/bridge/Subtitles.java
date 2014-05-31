package yaroslav.subtitles.bridge;

import java.util.NavigableMap;

/**
 * Created by yaroslav on 5/31/14.
 */
public class Subtitles {
    /**
     * startTime - subtitleUnit map
     */
    private NavigableMap<Integer, SubtitlesUnit> data;
    private String filmTitle;
    private String language;

    public Subtitles(NavigableMap<Integer, SubtitlesUnit> data) {
        this.data = data;
    }


}
