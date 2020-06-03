package exceptions;

public class TimeoutException extends Exception {
    private static String TIMEOUT_EXCEPTION_MESSAGE = "Waiting in wait function timed out";

    public TimeoutException() {
        super(TIMEOUT_EXCEPTION_MESSAGE);
    }

    public TimeoutException(String customMessage) {
        super(TIMEOUT_EXCEPTION_MESSAGE + ": " + customMessage);
    }
}
