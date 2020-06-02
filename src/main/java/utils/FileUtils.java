package utils;

import exceptions.TimeoutException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static utils.TimeoutUtils.isMaximumTimeInFunctionPassed;

public class FileUtils {
    // Todo: the name should be meaningful name that express the action it involved
    private static int WAIT_MILLISECONDS_WHEN_FILE_NOT_EXISTS = 500;

    public static File waitForFileToExist(String filePath, int maximumSecondsToWait) throws InterruptedException, TimeoutException {
        File file = new File(filePath);
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();
        while (!file.exists()) {
            sleep(WAIT_MILLISECONDS_WHEN_FILE_NOT_EXISTS);

            if (isMaximumTimeInFunctionPassed(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
                throw new TimeoutException("Timed out in waitForDownloadToStart");
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
        // the last index can be extracted to method and then is the var needed for the list
        int lastFileIndex = filesSortedByDate.size() - 1;
        return filesSortedByDate.get(lastFileIndex);

        //   return sortFilesByDate(getFilesByExtensionFrom(folderPath, extension)).get();
    }
}