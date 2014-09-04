package learn.english.model.entity.media;

import learn.english.model.entity.MediaItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.persistence.Entity;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Data @EqualsAndHashCode(callSuper = true)
@XmlDiscriminatorValue("Movie-classifier")
public class Movie extends MediaItem {
    String year;
}
