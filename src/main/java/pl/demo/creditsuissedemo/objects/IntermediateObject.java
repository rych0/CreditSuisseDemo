package pl.demo.creditsuissedemo.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntermediateObject {
    private String eventId;
    private String type;
    private String host;
    private Long startTime;
    private Long endTime;
}
