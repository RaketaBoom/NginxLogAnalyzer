package backend.academy.loganalyzer.exceptions;

public class GlobHandleException extends RuntimeException {
    private static final String MESSAGE = "Ошибка обработки glob-выражения";

    public GlobHandleException() {
        super(MESSAGE);
    }
}
