package utils;

import exceptions.TimeoutException;

import java.io.File;

import static java.lang.System.currentTimeMillis;
import static utils.TimeoutUtils.didMaximumTimePass;

public class DownloadUtils {
    private static int MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP = 500;

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
}
