package learn.english.web.controller;

import learn.english.model.entity.MediaItem;
import learn.english.model.utils.MediaItemType;

import javax.inject.Inject;
import javax.servlet.http.Part;
import lombok.Data;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 6/13/14.
 */
@Named
@ViewScoped
public @Data class MediaItemBean implements Serializable {
   // @EJB private MediaItemService mediaItemService;
    private MediaItem mediaItem;
    private MediaItemType type;
    private Part file;
    @Inject RestService restService;

    public void setType(MediaItemType type) {
/*        this.type = type;
        this.mediaItem = mediaItemService.generateItem(type);*/
        this.type = type;
        this.mediaItem = restService.path("item/type").path(type.toString()).get(MediaItem.class);
    }

}
