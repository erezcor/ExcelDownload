package utils;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutUtils {
    private static int MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP = 500;

    protected static boolean didMaximumTimePass(Long timeWhenEnteredFunctionInMilliSeconds, int maximumTimeInSeconds) {
        long timePassedInSeconds = MILLISECONDS.toSeconds(currentTimeMillis() - timeWhenEnteredFunctionInMilliSeconds);
        return timePassedInSeconds > maximumTimeInSeconds;
    }

    protected static void waitWhile(boolean booleanStatement, int maximumTimeToWaitInSeconds) throws InterruptedException {
        long timeWhenEnteredFunctionInMilliSeconds = currentTimeMillis();
        while (booleanStatement && !didMaximumTimePass(timeWhenEnteredFunctionInMilliSeconds, maximumTimeToWaitInSeconds)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP);
        }
    }
}
