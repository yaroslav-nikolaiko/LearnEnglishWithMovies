package yaroslav.learn.english.subtitles;

import yaroslav.learn.english.subtitles.bridge.Subtitles;
import yaroslav.learn.english.subtitles.exception.SubtitlesParserException;

/**
 * Created by yaroslav on 5/31/14.
 */
public interface SubtitlesParser {
    public Subtitles parse() throws SubtitlesParserException;
    public Subtitles parse(byte[] content)throws SubtitlesParserException;
}
