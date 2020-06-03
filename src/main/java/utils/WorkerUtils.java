package utils;

import entities.Worker;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static utils.ExcelUtils.getExcelFileAsStringLists;

public class WorkerUtils {
    private final static int SKIP_HEADLINES_ROW = 1;

    public static Worker getWorkerFromListByID(String id, List<Worker> workersList) {
        return workersList.stream().filter(worker -> worker.getId().equals(id)).findAny().orElseThrow();
    }

    public static List<Worker> getWorkersListFromExcelFile(File file) throws IOException, InvalidFormatException {
        return getExcelFileAsStringLists(file).stream().skip(SKIP_HEADLINES_ROW).map(Worker::new).collect(toList());
    }
}
