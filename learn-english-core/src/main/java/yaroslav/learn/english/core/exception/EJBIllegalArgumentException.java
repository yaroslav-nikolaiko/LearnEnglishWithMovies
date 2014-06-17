package yaroslav.learn.english.core.exception;

/**
 * Created by yaroslav on 6/14/14.
 */
public class EJBIllegalArgumentException extends Exception{
    //Map<Type, String> type_message_map;
    MessageType messageType;
    String explanation;

    public EJBIllegalArgumentException(String message){
        super(message);
        messageType = MessageType.INFO;
    }


    public EJBIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
        messageType = MessageType.INFO;
        //type_message_map = new HashMap<>();
    }

    public EJBIllegalArgumentException(String message, MessageType messageType, Throwable cause) {
        this(message, cause);
        this.messageType = messageType;
        //type_message_map = new HashMap<>();
    }

    public EJBIllegalArgumentException(String message, MessageType messageType) {
        this(message);
        this.messageType = messageType;
        //type_message_map = new HashMap<>();
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public static enum MessageType {
        INFO, WARN, ERROR
    }
}
