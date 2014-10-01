package learn.english.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Created by yaroslav on 9/28/14.
 */
@NoArgsConstructor
public @Data class AdvanceSubtitles {
    /**
     * startTime - subtitleUnit map
     */
    TreeMap<Integer,Unit> data = new TreeMap<>();
    //NavigableSet<Unit> data = new TreeSet<>();
    String videoFileName;

    @NoArgsConstructor
    static public @Data class Unit{
        //Integer time;
        /**
         * ex: Oh, my dear(дорогой, родной...) world.
         */
        String partialTranslatedText;
        String fullText;
        Map<String, String> newWords_translationMap = new HashMap<>();

/*        public

        public Unit time(Integer time){
            this.time = time;
            return this;
        }*/
    }
}
