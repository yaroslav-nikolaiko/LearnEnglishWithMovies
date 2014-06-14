package yaroslav.learn.english.core.exception;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaroslav on 6/14/14.
 */
public class EntityIllegalArgumentsException extends IllegalArgumentException{
    Map<Type, String> type_message_map;
    public EntityIllegalArgumentsException(String message) {
        super(message);
        type_message_map = new HashMap<>();

    }
}
