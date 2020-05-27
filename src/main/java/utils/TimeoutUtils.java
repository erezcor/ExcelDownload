package utils;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutUtils {
    private static int MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP = 500;

    protected static boolean didMaximumTimePass(Long timeWhenEnteredFunctionInMillis, int maximumTimeInSeconds) {
        long timePassedInSeconds = MILLISECONDS.toSeconds(currentTimeMillis() - timeWhenEnteredFunctionInMillis);
        return timePassedInSeconds > maximumTimeInSeconds;
    }

    protected static void waitWhile(boolean booleanStatement, int maximumTimeToWaitInSeconds) throws InterruptedException {
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();
        while (booleanStatement && !didMaximumTimePass(timeWhenEnteredFunctionInMillis, maximumTimeToWaitInSeconds)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP);
        }
    }
}
