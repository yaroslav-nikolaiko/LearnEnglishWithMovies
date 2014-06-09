package yaroslav.learn.english.subtitles;

import yaroslav.learn.english.subtitles.bridge.Subtitles;
import yaroslav.learn.english.subtitles.exception.SubtitlesParserException;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesManager {
    private Subtitles subtitles;
    private SubtitlesParser parser;

    public SubtitlesManager(String filePath) throws SubtitlesParserException {
        parser = new SubtitlesParserTimeTextObjectImpl(filePath);
        subtitles = parser.parse();
    }

    public SubtitlesManager(byte[] content) throws SubtitlesParserException {
        parser = new SubtitlesParserTimeTextObjectImpl(content);
        subtitles = parser.parse();
    }

    public SubtitlesManager() {
        parser = new SubtitlesParserTimeTextObjectImpl();
    }

    public Subtitles getSubtitles() {
        return subtitles;
    }

    public Subtitles getSubtitles(byte[] content) throws SubtitlesParserException {
        subtitles = parser.parse(content);
        return subtitles;
    }

}
