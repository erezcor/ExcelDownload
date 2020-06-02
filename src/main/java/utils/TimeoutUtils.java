package utils;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutUtils {
    protected static boolean isMaximumTimeInFunctionPassed(Long timeWhenEnteredFunctionInMillis, int maximumTimeInSeconds) {
        // todo extract to method named getTimePassed
        return getTimePassedInSeconds(timeWhenEnteredFunctionInMillis) > maximumTimeInSeconds;
    }

    // todo new function
    private static long getTimePassedInSeconds(Long timeWhenEnteredFunctionInMillis) {
        return MILLISECONDS.toSeconds(currentTimeMillis() - timeWhenEnteredFunctionInMillis);
    }
}
