package yaroslav.learn.english.subtitles.exception;

/**
 * Created by yaroslav on 5/31/14.
 */
public class SubtitlesParserException extends Exception {
    public SubtitlesParserException(String msg){
        super(msg);
    }

    public SubtitlesParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
