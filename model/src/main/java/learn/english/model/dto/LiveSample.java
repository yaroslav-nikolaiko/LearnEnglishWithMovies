package learn.english.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * Created by yaroslav on 9/28/14.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class LiveSample {
    String videoFileName;
    Integer timeFrame;
}
