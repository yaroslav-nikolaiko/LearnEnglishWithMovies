package learn.english.model.entity.wraper;

import learn.english.model.entity.WordCell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 9/7/14.
 */
public @Data @AllArgsConstructor @NoArgsConstructor class WordCells {
    Collection<WordCell> wordCells;
}
