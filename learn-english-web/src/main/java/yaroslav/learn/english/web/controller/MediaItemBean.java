package yaroslav.learn.english.web.controller;

import org.primefaces.model.UploadedFile;
import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.entity.media.TVShow;
import yaroslav.learn.english.core.util.MediaItemType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/13/14.
 */
@Named
@RequestScoped
public class MediaItemBean implements Serializable {
    private MediaItem mediaItem;
    private UploadedFile file;

    @PostConstruct
    void init(){
        mediaItem = new TVShow();
    }

    public MediaItem getMediaItem() {
         return mediaItem;
    }

    public void setMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
