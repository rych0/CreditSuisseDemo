package pl.demo.creditsuissedemo.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogFileRecord {
    private String id;
    private String state;
    private String type = "";
    private String host = "";
    private Long timestamp;
}
