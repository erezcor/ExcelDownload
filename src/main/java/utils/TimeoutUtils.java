package utils;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutUtils {
    protected static boolean isMaximumTimeInFunctionPassed(Long timeWhenEnteredFunctionInMillis, int maximumTimeInSeconds) {
        return getTimePassedInSeconds(timeWhenEnteredFunctionInMillis) > maximumTimeInSeconds;
    }

    private static long getTimePassedInSeconds(Long timeWhenEnteredFunctionInMillis) {
        return MILLISECONDS.toSeconds(currentTimeMillis() - timeWhenEnteredFunctionInMillis);
    }
}
