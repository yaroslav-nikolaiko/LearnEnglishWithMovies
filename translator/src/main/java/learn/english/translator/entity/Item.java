package learn.english.translator.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
/*import org.springframework.data.mongodb.core.mapping.Document;*/

/**
 * Created by yaroslav on 9/21/14.
 */
/*@Document*/
@Data
@NoArgsConstructor
public class Item {
    String text;
    String translation;
}
