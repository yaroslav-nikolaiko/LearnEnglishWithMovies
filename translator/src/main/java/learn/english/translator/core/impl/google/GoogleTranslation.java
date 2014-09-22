package learn.english.translator.core.impl.google;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yaroslav on 9/22/14.
 */
@Data @NoArgsConstructor
public class GoogleTranslation {
    private Sentences[] sentences;
    private String en;
    private Integer server_time;
}

@Data @NoArgsConstructor
class Sentences {
    private String trans;
    private String orig;
    private String translit;
    private String src_translit;
}
