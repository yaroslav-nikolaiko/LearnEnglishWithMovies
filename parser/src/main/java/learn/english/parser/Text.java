package learn.english.parser;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by yaroslav on 8/6/14.
 */
public interface Text {
    public Set<String> words();
    public default boolean contains(String word) {
        return words().contains(word);
    }
}
