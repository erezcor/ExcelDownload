package utils;

import java.io.File;

import static constants.SystemConstants.DOWNLOADS_DIRECTORY_PATH;
import static utils.FileUtils.getNumberOfFilesByExtensionFrom;
import static utils.TimeoutUtils.waitWhile;

public class DownloadUtils {
    private final static String CHROME_DOWNLOAD_EXTENSION = "crdownload";
    private final static int ZERO_FILES_DOWNLOADING = 0;

    public static File waitForFileToExist(String filePath, int maximumSecondsToWait) throws InterruptedException {
        File file = new File(filePath);
        waitWhile(!file.exists(), maximumSecondsToWait);
        return file;
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
        return getNumberOfFilesByExtensionFrom(DOWNLOADS_DIRECTORY_PATH, CHROME_DOWNLOAD_EXTENSION);
    }
}
