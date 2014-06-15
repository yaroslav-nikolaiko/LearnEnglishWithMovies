package yaroslav.learn.english.core.exception;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaroslav on 6/14/14.
 */
public class EJBIllegalArgumentsException extends Exception{
    Map<Type, String> type_message_map;
    public EJBIllegalArgumentsException(String message, Throwable cause) {
        super(message, cause);
        type_message_map = new HashMap<>();

    }
}
