package backend.academy.loganalyzer.exceptions;

public class UrlReadException extends RuntimeException {
    private static final String MESSAGE = "Ошибка чтения URL";

    public UrlReadException() {
        super(MESSAGE);
    }
}
