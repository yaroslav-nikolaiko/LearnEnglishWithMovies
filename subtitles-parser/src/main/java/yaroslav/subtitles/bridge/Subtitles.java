package yaroslav.subtitles.bridge;

import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;

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


    public Set<String> getAllWords() {
        Set<String> allWords = new HashSet<>();
        for(SubtitlesUnit unit : data.values()){
            allWords.addAll(unit.getWords());
        }
        return allWords;
    }


}
