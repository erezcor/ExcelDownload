package utils;

import entities.Worker;

import java.util.List;

public class WorkerUtils {
    public static Worker getWorkerFromListByID(String id, List<Worker> workersList) {
        return workersList.stream().filter(worker -> worker.getId().equals(id)).findAny().orElseThrow();
    }
}
