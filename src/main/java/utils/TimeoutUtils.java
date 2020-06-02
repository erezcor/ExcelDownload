package utils;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutUtils {
    protected static boolean isMaximumTimeInFunctionPassed(Long timeWhenEnteredFunctionInMillis, int maximumTimeInSeconds) {
        long timePassedInSeconds = MILLISECONDS.toSeconds(currentTimeMillis() - timeWhenEnteredFunctionInMillis);
        // extract to method named getTimePassed
        return timePassedInSeconds > maximumTimeInSeconds;
    }
}
