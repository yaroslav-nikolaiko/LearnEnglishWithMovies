package learn.english.core.service.textparser;

import java.util.Collection;

/**
 * Created by yaroslav on 8/2/14.
 */

public interface TextParser {
    Collection<String> extractWords(byte[] data);
}
