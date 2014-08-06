package learn.english.parser.subtitles;


import learn.english.parser.Parser;
import learn.english.parser.exception.ParserException;
import learn.english.parser.subtitles.bridge.Subtitles;

/**
 * Created by yaroslav on 5/31/14.
 */
public interface SubtitlesParser extends Parser {
    public Subtitles parse() throws ParserException;
    public Subtitles parse(byte[] content)throws ParserException;
}
