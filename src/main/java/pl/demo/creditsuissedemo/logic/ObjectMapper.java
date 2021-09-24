package pl.demo.creditsuissedemo.logic;

import org.apache.commons.lang3.StringUtils;
import pl.demo.creditsuissedemo.Objects.IntermediateObject;
import pl.demo.creditsuissedemo.Objects.LogFileRecord;
import pl.demo.creditsuissedemo.db.LogValueEntity;

import static pl.demo.creditsuissedemo.Objects.StaticCodes.FINISHED_CODE;
import static pl.demo.creditsuissedemo.Objects.StaticCodes.STARTED_CODE;

public class ObjectMapper {


    public static IntermediateObject mapLogFileRecordToIntermediate(LogFileRecord logFileRecord) {
        IntermediateObject intermediateObject = new IntermediateObject();
        intermediateObject.setEventId(logFileRecord.getId());
        if (StringUtils.isNoneBlank(logFileRecord.getHost())) {
            intermediateObject.setHost(logFileRecord.getHost());
        } else {
            intermediateObject.setHost("");
        }
        if (StringUtils.isNoneBlank(logFileRecord.getType())) {
            intermediateObject.setType(logFileRecord.getType());
        } else {
            intermediateObject.setType("");
        }
        if (STARTED_CODE.equals(logFileRecord.getState())) {
            intermediateObject.setStartTime(logFileRecord.getTimestamp());
        }
        if (FINISHED_CODE.equals(logFileRecord.getState())) {
            intermediateObject.setEndTime(logFileRecord.getTimestamp());
        }
        return intermediateObject;
    }

    public static LogValueEntity mapIntermediateObjectToEntity(IntermediateObject intermediateObject) {
        LogValueEntity logValueEntity = new LogValueEntity();
        logValueEntity.setEventId(intermediateObject.getEventId());
        logValueEntity.setHost(intermediateObject.getHost());
        logValueEntity.setType(intermediateObject.getType());
        Long duration = intermediateObject.getEndTime() - intermediateObject.getStartTime();
        logValueEntity.setDuration(duration);
        if (duration > 4) {
            logValueEntity.setAlert(true);
        }
        return logValueEntity;
    }
}
