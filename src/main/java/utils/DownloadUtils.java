package utils;

import exceptions.TimeoutException;

import java.io.File;

import static constants.SystemConstants.DOWNLOADS_DIRECTORY_PATH;
import static java.lang.System.currentTimeMillis;
import static utils.FileUtils.getNumberOfFilesByExtensionFrom;
import static utils.TimeoutUtils.didMaximumTimePass;

public class DownloadUtils {
    private static int MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP = 500;
    private final static String CHROME_DOWNLOAD_EXTENSION = "crdownload";
    private final static int ZERO_FILES_DOWNLOADING = 0;

    public static File waitForFileToExist(String filePath, int maximumSecondsToWait) throws InterruptedException, TimeoutException {
        File file = new File(filePath);
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();
        while (!file.exists() &&
                !didMaximumTimePass(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP);
        }

        if (didMaximumTimePass(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            throw new TimeoutException("Timed out in waitForDownloadToStart");
        }

        return file;
    }

    public static void waitForDownloadToStart(int maximumSecondsToWait) throws InterruptedException, TimeoutException {
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();
        while (!didFileDownloadStart() && !didMaximumTimePass(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP);
        }

        if (didMaximumTimePass(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            throw new TimeoutException("Timed out in waitForDownloadToStart");
        }
    }

    public static void waitForDownloadsToFinish(int maximumSecondsToWait) throws InterruptedException, TimeoutException {
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();
        while (!didFilesDownloadFinish() && !didMaximumTimePass(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP);
        }

        if (didMaximumTimePass(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            throw new TimeoutException("Timed out in waitForDownloadToStart");
        }
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
