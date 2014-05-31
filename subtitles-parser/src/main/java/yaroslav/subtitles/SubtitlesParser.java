package yaroslav.subtitles;

import yaroslav.subtitles.bridge.Subtitles;
import yaroslav.subtitles.exception.SubtitlesParserException;

/**
 * Created by yaroslav on 5/31/14.
 */
public interface SubtitlesParser {
    public Subtitles parse() throws SubtitlesParserException;
}
