package pl.demo.creditsuissedemo.logic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.demo.creditsuissedemo.db.LogValueEntity;
import pl.demo.creditsuissedemo.db.LogValuesRepository;
import pl.demo.creditsuissedemo.objects.CreditSuisseException;
import pl.demo.creditsuissedemo.objects.IntermediateObject;
import pl.demo.creditsuissedemo.objects.LogFileRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.demo.creditsuissedemo.objects.StaticCodes.FINISHED_CODE;
import static pl.demo.creditsuissedemo.objects.StaticCodes.STARTED_CODE;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainLogic {
    private final FileReaderService fileReaderService;
    private final LogValuesRepository logValuesRepository;

    public void runAll(String path) {
        List<LogFileRecord> listOfRecords = new ArrayList<>();
        try {
            listOfRecords = fileReaderService.parseListOfJsonsToObjects(fileReaderService.readFromFile(path));
        } catch (CreditSuisseException cse) {
            log.info(cse.getMessage());
        }
        if (!listOfRecords.isEmpty()) {
            List<IntermediateObject> intermediateObjectList = concatenateObjects(listOfRecords);
            calculateDurationAndWriteToDB(intermediateObjectList);
        }
    }

    private List<IntermediateObject> concatenateObjects(List<LogFileRecord> listOfRecords) {
        Map<String, IntermediateObject> intermediateObjectMap = new HashMap<>();
        listOfRecords.forEach(it -> {
            if (intermediateObjectMap.containsKey(it.getId())) {
                IntermediateObject intermediateObject = intermediateObjectMap.get(it.getId());
                if (STARTED_CODE.equals(it.getState())) {
                    intermediateObject.setStartTime(it.getTimestamp());
                } else if (FINISHED_CODE.equals(it.getState())) {
                    intermediateObject.setEndTime(it.getTimestamp());
                }
            } else {
                intermediateObjectMap.put(it.getId(), ObjectMapper.mapLogFileRecordToIntermediate(it));
            }
        });
        return new ArrayList<>(intermediateObjectMap.values());
    }

    private void calculateDurationAndWriteToDB(List<IntermediateObject> intermediateObjects) {
        List<LogValueEntity> listValues = intermediateObjects.parallelStream().map(ObjectMapper::mapIntermediateObjectToEntity).collect(Collectors.toList());
        logValuesRepository.saveAll(listValues);
    }

}
