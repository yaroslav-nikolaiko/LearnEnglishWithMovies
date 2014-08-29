package learn.english.core.logger.message;

import learn.english.core.entity.Dictionary;
import org.apache.logging.log4j.message.Message;

/**
 * Created by yaroslav on 8/29/14.
 */
public class CRUDMessage implements Message {
    protected StringBuilder text = new StringBuilder();

    public static Message create(Object object){
        CRUDMessage message = new CRUDMessage();
        if(object != null){
            String className = object.getClass().getSimpleName();
            message.text.append(String.format("%s %s was created", className, object));
        }
        else
            message.text.append(String.format("Failed to create something. NullPointer"));
        return message;
    }

    public static Message update(Object object){
        CRUDMessage message = new CRUDMessage();
        if(object != null){
            String className = object.getClass().getSimpleName();
            message.text.append(String.format("%s %s was updated", className, object));
        }
        else
            message.text.append(String.format("Failed to update something. NullPointer"));
        return message;
    }

    public static Message remove(Object object){
        CRUDMessage message = new CRUDMessage();
        if(object != null){
            String className = object.getClass().getSimpleName();
            message.text.append(String.format("%s %s was removed", className, object));
        }
        else
            message.text.append(String.format("Failed to remove something. NullPointer"));
        return message;
    }

    @Override
    public String getFormattedMessage() {
        return text.toString();
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}
