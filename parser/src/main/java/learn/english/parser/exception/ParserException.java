package learn.english.parser.exception;

/**
 * Created by yaroslav on 5/31/14.
 */
public class ParserException extends Exception {
    public ParserException(String msg){
        super(msg);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
