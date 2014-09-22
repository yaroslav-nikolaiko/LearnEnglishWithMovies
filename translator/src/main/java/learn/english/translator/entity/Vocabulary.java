package learn.english.translator.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
/*import org.springframework.data.mongodb.core.mapping.Document;*/

import java.util.List;
import java.util.Map;

/**
 * Created by yaroslav on 9/21/14.
 */
/*@Document*/
@Data @NoArgsConstructor
public class Vocabulary {
    String name;
    String from;
    String to;
    Map<String, String> data;
}
