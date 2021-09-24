package pl.demo.creditsuissedemo.logic;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.demo.creditsuissedemo.Objects.CreditSuisseException;
import pl.demo.creditsuissedemo.Objects.ExceptionCodes;
import pl.demo.creditsuissedemo.Objects.LogFileRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileReaderService {
    private JsonFactory jsonFactory = new JsonFactory();
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<String> readFromFile(String path) throws CreditSuisseException {
        List<String> fileLines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                fileLines.add(currentLine);
            }
            reader.close();
        } catch (IOException e) {
            throw new CreditSuisseException(ExceptionCodes.FILE_ERROR);
        }
        if (fileLines.isEmpty()) {
            throw new CreditSuisseException(ExceptionCodes.EMPTY_LIST);
        }

        return fileLines;
    }

    public List<LogFileRecord> parseListOfJsonsToObjects(List<String> lineList) {
        return lineList.parallelStream().map(this::parseOneJsonToObject).collect(Collectors.toList());
    }

    private LogFileRecord parseOneJsonToObject(String json) {
        LogFileRecord logFileRecord = new LogFileRecord();
        try {
            JsonParser jsonParser = jsonFactory.createParser(json);
            objectMapper.findAndRegisterModules();
            logFileRecord = objectMapper.readValue(jsonParser, new TypeReference<LogFileRecord>() {
            });
        } catch (IOException e) {
            log.info("Record parsing failed");
        }
        return logFileRecord;
    }

}
