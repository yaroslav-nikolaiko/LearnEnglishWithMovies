package learn.english.web.exception;

import javax.faces.application.FacesMessage;

/**
 * Created by yaroslav on 6/15/14.
 */
public class WebException extends IllegalArgumentException {
    //Map<Type, String> type_message_map;
    FacesMessage.Severity messageType;
    String explanation;

    public WebException(String message){
        super(message);
    }


    public WebException(String message, Throwable cause) {
        super(message, cause);
        //type_message_map = new HashMap<>();
    }

    public WebException(String message, FacesMessage.Severity messageType, Throwable cause) {
        this(message, cause);
        this.messageType = messageType;
        //type_message_map = new HashMap<>();
    }

    public WebException(String message, FacesMessage.Severity messageType) {
        this(message);
        this.messageType = messageType;
        //type_message_map = new HashMap<>();
    }

    public FacesMessage.Severity getMessageType() {
        return messageType;
    }

    public void setMessageType(FacesMessage.Severity messageType) {
        this.messageType = messageType;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
