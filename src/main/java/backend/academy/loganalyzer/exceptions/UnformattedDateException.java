package backend.academy.loganalyzer.exceptions;

public class UnformattedDateException extends RuntimeException {
    private static final String MESSAGE = "Неформатированная дата";

    public UnformattedDateException() {
        super(MESSAGE);
    }
}
