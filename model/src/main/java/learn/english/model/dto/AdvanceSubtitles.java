package learn.english.model.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by yaroslav on 9/28/14.
 */
public @Data class AdvanceSubtitles {
    /**
     * startTime - subtitleUnit map
     */
    NavigableMap<Integer,Unit> data = new TreeMap<>();
    String videoFileName;


    static public @Data class Unit{
        /**
         * ex: Oh, my dear(дорогой, родной...) world.
         */
        String partialTranslatedText;
        String fullText;
        Map<String, String> newWords_translationMap = new HashMap<>();
    }
}
