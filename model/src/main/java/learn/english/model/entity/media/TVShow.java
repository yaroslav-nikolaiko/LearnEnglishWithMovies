package learn.english.model.entity.media;

import learn.english.model.adapter.MediaItemAdapter;
import learn.english.model.entity.MediaItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.persistence.Entity;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by yaroslav on 6/2/14.
 */
@Entity
@Data @EqualsAndHashCode(callSuper = true)
@XmlDiscriminatorValue("TVShow-classifier")
public class TVShow extends MediaItem {
    String season;
    String episode;
}
