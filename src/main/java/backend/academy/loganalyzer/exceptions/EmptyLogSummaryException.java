package backend.academy.loganalyzer.exceptions;

public class EmptyLogSummaryException extends RuntimeException {
    private static final String MESSAGE = "Пустой LogSummary";

    public EmptyLogSummaryException() {
        super(MESSAGE);
    }
}
