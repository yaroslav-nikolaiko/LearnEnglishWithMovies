package learn.english.core.logger.message;

import learn.english.core.entity.User;
import org.apache.logging.log4j.message.Message;

/**
 * Created by yaroslav on 8/29/14.
 */
public class UserMessage extends CRUDMessage {
    public static Message signUp(User user){
        UserMessage message = new UserMessage();
        if(user!=null)
            message.text.append(String.format("User %s, %s has been singed up", user.getName(), user.getEmail()));
        else
            message.text.append(String.format("Failed to Sign Up"));
        return message;
    }

    public static Message logIn(User user){
        UserMessage message = new UserMessage();
        if(user!=null)
            message.text.append(String.format("User %s has been logged in", user.getName()));
        else
            message.text.append(String.format("Failed to Log In"));
        return message;
    }
}
