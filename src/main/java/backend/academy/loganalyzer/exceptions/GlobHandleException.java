package backend.academy.loganalyzer.exceptions;

public class GlobHandleException extends RuntimeException {
    private static final String MESSAGE = "Неформатированная дата";

    public GlobHandleException() {
        super(MESSAGE);
    }
}
