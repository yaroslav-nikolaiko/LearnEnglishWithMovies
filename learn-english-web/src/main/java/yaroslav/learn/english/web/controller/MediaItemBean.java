package yaroslav.learn.english.web.controller;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.entity.media.TVShow;
import yaroslav.learn.english.core.util.MediaItemType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/13/14.
 */
@Named
@RequestScoped
public class MediaItemBean implements Serializable {
    private MediaItem mediaItem;
    private Part file;

    @PostConstruct
    void init(){
//        if(conversation.isTransient())
//            conversation.begin();
        mediaItem = new TVShow();
    }

    public MediaItem getMediaItem() {
         return mediaItem;
    }

    public void setMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void listener(){

    }


}
