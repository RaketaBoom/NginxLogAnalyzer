package backend.academy.loganalyzer.exceptions;

public class UnforamttedDateException extends RuntimeException {
    private static final String MESSAGE = "Неформатированная дата";

    public UnforamttedDateException() {
        super(MESSAGE);
    }
}
