package learn.english.core.realtime.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yaroslav on 11/7/14.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class TimeDTO {
    Integer previousTimeFrame;
    Integer timeFrame;
    Integer nextTimeFrame;

    public String createJSON() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(String.format("\"previousTimeFrame\": \"%s\",",previousTimeFrame));
        builder.append(String.format("\"timeFrame\": \"%s\",",timeFrame));
        builder.append(String.format("\"nextTimeFrame\": \"%s\"",nextTimeFrame));
        builder.append("}");
        return builder.toString();
    }
}
