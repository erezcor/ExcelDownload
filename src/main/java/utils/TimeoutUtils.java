package utils;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutUtils {
    protected static boolean didMaximumTimePass(Long timeWhenEnteredFunction, int maximumTimeInSeconds) {
        long timePassedInSeconds = MILLISECONDS.toSeconds(currentTimeMillis() - timeWhenEnteredFunction);
        return timePassedInSeconds > maximumTimeInSeconds;
    }
}
