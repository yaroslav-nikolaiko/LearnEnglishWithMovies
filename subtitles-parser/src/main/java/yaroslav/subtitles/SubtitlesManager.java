package yaroslav.subtitles;

import yaroslav.subtitles.bridge.Subtitles;
import yaroslav.subtitles.exception.SubtitlesParserException;

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
        //parser = new SubtitlesParserTimeTextObjectImpl(filePath);
        //subtitles = parser.parse();
    }

    public Subtitles getSubtitles() {
        return subtitles;
    }

}
