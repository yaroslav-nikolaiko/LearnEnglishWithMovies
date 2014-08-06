package learn.english.parser.subtitles;


import learn.english.parser.exception.ParserException;
import learn.english.parser.subtitles.bridge.Subtitles;
import learn.english.parser.subtitles.timeTextObjectImpl.SubtitlesParserTimeTextObjectImpl;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesManager {
    private Subtitles subtitles;
    private SubtitlesParser parser;

    public SubtitlesManager(String filePath) throws ParserException {
        parser = new SubtitlesParserTimeTextObjectImpl(filePath);
        subtitles = parser.parse();
    }

    public SubtitlesManager(byte[] content) throws ParserException {
        parser = new SubtitlesParserTimeTextObjectImpl(content);
        subtitles = parser.parse();
    }

    public SubtitlesManager() {
        parser = new SubtitlesParserTimeTextObjectImpl();
    }

    public Subtitles getSubtitles() {
        return subtitles;
    }

    public Subtitles getSubtitles(byte[] content) throws ParserException {
        subtitles = parser.parse(content);
        return subtitles;
    }

}
