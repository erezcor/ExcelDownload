package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static utils.TimeoutUtils.didMaximumTimePass;

public class FileUtils {
    private final static int MILLISECONDS_TO_WAIT_EACH_LOOP = 500;
    private final static String CHROME_DOWNLOAD_EXTENSION = "crdownload";
    private final static int ZERO_FILES_DOWNLOADING = 0;

    private final static String DOWNLOAD_FOLDER_PATH = "C:\\Users\\Erez Corech\\Downloads\\";

    public static void waitForFileToExist(String filePath, int maximumSecondsToWait) throws InterruptedException {
        File file = new File(filePath);
        Long timeWhenEnteredFunction = currentTimeMillis();
        while (!file.exists() && !didMaximumTimePass(timeWhenEnteredFunction, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_EACH_LOOP);
        }
    }

    public static void waitForDownloadToStart(int maximumSecondsToWait) throws InterruptedException {
        Long timeWhenEnteredFunction = currentTimeMillis();
        while (!didFileStartDownload() &&
                !didMaximumTimePass(timeWhenEnteredFunction, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_EACH_LOOP);
        }
    }

    public static void waitForDownloadsToFinish(int maximumSecondsToWait) throws InterruptedException {
        Long timeWhenEnteredFunction = currentTimeMillis();
        while (!didFilesFinishDownload() &&
                !didMaximumTimePass(timeWhenEnteredFunction, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_EACH_LOOP);
        }
    }

    private static boolean didFilesFinishDownload() {
        return getNumberOfFilesDownloading() == ZERO_FILES_DOWNLOADING;
    }

    private static boolean didFileStartDownload() {
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

    public static File getLatestFileFromFolderByExtension(String folderPath, String extension) {
        List<File> filesSortedByDate = sortFilesByDate(getFilesByExtensionFrom(folderPath, extension));
        int lastFileIndex = filesSortedByDate.size() - 1;
        return filesSortedByDate.get(lastFileIndex);
    }

    public static void deleteFileAt(String filePath) {
        new File(filePath).delete();
    }
}