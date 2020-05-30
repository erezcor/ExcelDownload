package utils;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutUtils {
    // todo make private when "waitWhile" function is fixed
    private static int MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP = 500;

    protected static boolean didMaximumTimePass(Long timeWhenEnteredFunctionInMillis, int maximumTimeInSeconds) {
        long timePassedInSeconds = MILLISECONDS.toSeconds(currentTimeMillis() - timeWhenEnteredFunctionInMillis);
        return timePassedInSeconds > maximumTimeInSeconds;
    }
}
