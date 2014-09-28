package learn.english.parser.subtitles.bridge;


import learn.english.parser.Text;
import lombok.Getter;

import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 5/31/14.
 */
public class Subtitles implements Text {
    /**
     * startTime - subtitleUnit map
     */
    @Getter
    private NavigableMap<Integer, SubtitlesUnit> data;
    private String filmTitle;
    private String language;

    public Subtitles(NavigableMap<Integer, SubtitlesUnit> data) {
        this.data = data;
    }


    @Override
    public Set<String> words() {
/*        Set<String> allWords = new HashSet<>();
        for(SubtitlesUnit unit : data.values()){
            allWords.addAll(unit.getWords());
        }*/
        return data.values().stream().flatMap((item)->item.getWords().stream()).collect(toSet());
    }

/*    @Override
    public boolean contains(String word) {
        return words().contains(word);
    }*/


}
