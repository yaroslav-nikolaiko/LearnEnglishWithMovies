package learn.english.web.controller;

import lombok.Data;
import learn.english.core.entity.MediaItem;
import learn.english.core.service.MediaItemService;
import learn.english.core.utils.MediaItemType;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/13/14.
 */
@Named
@ViewScoped
public @Data class MediaItemBean implements Serializable {
    @EJB private MediaItemService mediaItemService;
    private MediaItem mediaItem;
    private MediaItemType type;
    private Part file;

    public void setType(MediaItemType type) {
        this.type = type;
        this.mediaItem = mediaItemService.generateItem(type);
    }

}