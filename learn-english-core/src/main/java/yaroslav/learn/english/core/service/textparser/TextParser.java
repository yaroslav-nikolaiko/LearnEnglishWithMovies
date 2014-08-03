package yaroslav.learn.english.core.service.textparser;

import yaroslav.learn.english.core.entity.WordCell;

import java.util.Collection;

/**
 * Created by yaroslav on 8/2/14.
 */

public interface TextParser {
    Collection<String> extractWords(byte[] data);
}
