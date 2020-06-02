package utils;

import exceptions.TimeoutException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static utils.TimeoutUtils.isMaximumTimeInFunctionPassed;

public class FileUtils {
    private static int MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP = 500;

    public static File waitForFileToExist(String filePath, int maximumSecondsToWait) throws InterruptedException, TimeoutException {
        File file = new File(filePath);
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();
        while (!file.exists() &&
                !isMaximumTimeInFunctionPassed(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP);
        }

        if (isMaximumTimeInFunctionPassed(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            throw new TimeoutException("Timed out in waitForDownloadToStart");
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
        int lastFileIndex = filesSortedByDate.size() - 1;
        return filesSortedByDate.get(lastFileIndex);
    }
}