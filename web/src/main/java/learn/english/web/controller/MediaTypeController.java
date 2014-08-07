package learn.english.web.controller;

import lombok.Data;
import learn.english.core.utils.MediaItemType;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by yaroslav on 8/1/14.
 */
@Named
@SessionScoped
public @Data class  MediaTypeController implements Serializable {
    MediaItemType type;

}
