package utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import static constants.SystemConstants.DOWNLOAD_FOLDER_PATH;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static utils.TimeoutUtils.waitWhile;

public class FileUtils {
    private final static String CHROME_DOWNLOAD_EXTENSION = "crdownload";
    private final static int ZERO_FILES_DOWNLOADING = 0;

    public static void waitForFileToExist(String filePath, int maximumSecondsToWait) throws InterruptedException {
        File file = new File(filePath);
        waitWhile(!file.exists(), maximumSecondsToWait);
    }

    public static void waitForDownloadToStart(int maximumSecondsToWait) throws InterruptedException {
        waitWhile(!didFileDownloadStart(), maximumSecondsToWait);
    }

    public static void waitForDownloadsToFinish(int maximumSecondsToWait) throws InterruptedException {
        waitWhile(!didFilesDownloadFinish(), maximumSecondsToWait);
    }

    private static boolean didFilesDownloadFinish() {
        return getNumberOfFilesDownloading() == ZERO_FILES_DOWNLOADING;
    }

    private static boolean didFileDownloadStart() {
        return getNumberOfFilesDownloading() > ZERO_FILES_DOWNLOADING;
    }

    private static int getNumberOfFilesDownloading() {
        return getFilesByExtensionFrom(DOWNLOAD_FOLDER_PATH, CHROME_DOWNLOAD_EXTENSION).size();
    }

    private static List<File> getFilesByExtensionFrom(String folderPath, String extension) {
        return Arrays.asList(new File(folderPath).listFiles(filterFilesByExtension(extension)));
    }

    private static FileFilter filterFilesByExtension(String extension) {
        return file -> file.getName().endsWith(extension);
    }

    private static List<File> sortFilesByDate(List<File> files) {
       return files.stream().sorted(comparingLong(File::lastModified)).collect(toList());
    }

    protected static File getLatestFileFromFolderByExtension(String folderPath, String extension) {
        List<File> filesSortedByDate = sortFilesByDate(getFilesByExtensionFrom(folderPath, extension));
        int lastFileIndex = filesSortedByDate.size() - 1;
        return filesSortedByDate.get(lastFileIndex);
    }

    public static void deleteFileAt(String filePath) {
        new File(filePath).delete();
    }
}