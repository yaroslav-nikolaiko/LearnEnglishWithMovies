package learn.english.web.controller;

import lombok.Data;
import learn.english.model.entity.MediaItem;
import learn.english.web.rest.MediaItemService;
import learn.english.model.utils.MediaItemType;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/13/14.
 */
@Named
@ViewScoped
public @Data class MediaItemBean implements Serializable {
    @Inject private MediaItemService mediaItemService;
    private MediaItem mediaItem;
    private MediaItemType type;
    private Part file;

    public void setType(MediaItemType type) {
        this.type = type;
        this.mediaItem = mediaItemService.generateItem(type);
    }

}
