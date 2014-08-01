package yaroslav.learn.english.web.controller;

import lombok.Data;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.entity.media.TVShow;
import yaroslav.learn.english.core.service.MediaItemService;
import yaroslav.learn.english.core.util.MediaItemType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/13/14.
 */
@Named
@SessionScoped
public class MediaItemBean implements Serializable {
    @EJB private MediaItemService mediaItemService;
    private MediaItem mediaItem;
    private MediaItemType type;
    private Part file;

/*    @PostConstruct
    void init(){
//        if(conversation.isTransient())
//            conversation.begin();
        mediaItem = new TVShow();
    }*/

    public void setType(MediaItemType type) {
        this.type = type;
        this.mediaItem = mediaItemService.generateItem(type);
    }

    public MediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public MediaItemType getType() {
        return type;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
}
