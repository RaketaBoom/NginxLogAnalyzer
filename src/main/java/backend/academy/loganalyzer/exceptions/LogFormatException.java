package backend.academy.loganalyzer.exceptions;

public class LogFormatException extends RuntimeException {
    private static final String MESSAGE = "Лог не соответствует формату";

    public LogFormatException() {
        super(MESSAGE);
    }
}
