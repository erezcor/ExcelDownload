package utils;

import exceptions.TimeoutException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.*;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static utils.ListUtils.getLastElementFromList;
import static utils.TimeoutUtils.isMaximumTimeInFunctionPassed;

public class FileUtils {
    private static int WAIT_MILLISECONDS_WHILE_FILE_DOESNT_EXIST = 500;

    public static File waitForFileToExist(String filePath, int maximumSecondsToWait) throws InterruptedException, TimeoutException {
        File file = new File(filePath);
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();

        while (!file.exists()) {
            sleep(WAIT_MILLISECONDS_WHILE_FILE_DOESNT_EXIST);

            if (isMaximumTimeInFunctionPassed(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
                throw new TimeoutException("Timed out in waitForFileToExist");
            }
        }

        return file;
    }

    protected static int getNumberOfFilesByExtensionFrom(String folderPath, String extension) {
        return getFilesByExtensionFrom(folderPath, extension).size();
    }

    protected static List<File> getFilesByExtensionFrom(String folderPath, String extension) {
        return getAllFilesFromDirectory(folderPath).stream()
                .filter(file -> file.getName().endsWith(extension))
                .collect(toList());
    }

    protected static List<File> getAllFilesFromDirectory(String folderPath) {
        return Arrays.asList(new File(folderPath).listFiles());
    }

    private static List<File> sortFilesByDate(List<File> files) {
       return files.stream().sorted(comparingLong(File::lastModified)).collect(toList());
    }

    protected static File getLatestFileFromFolderByExtension(String folderPath, String extension) {
        List<File> filesSortedByDate = sortFilesByDate(getFilesByExtensionFrom(folderPath, extension));
        return getLastElementFromList(filesSortedByDate);
    }
}