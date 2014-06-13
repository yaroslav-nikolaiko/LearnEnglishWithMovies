package yaroslav.learn.english.web.controller;

import yaroslav.learn.english.core.entity.media.MediaItem;
import yaroslav.learn.english.core.util.MediaItemType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by yaroslav on 6/13/14.
 */
@Named
@RequestScoped
public class MediaItemBean {
    private MediaItem mediaItem;
    private MediaItemType type;

    public MediaItemType getType() {
        return type;
    }

    public void setType(MediaItemType type) {
        this.type = type;
    }

}
