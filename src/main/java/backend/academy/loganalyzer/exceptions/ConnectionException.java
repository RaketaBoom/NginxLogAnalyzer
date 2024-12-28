package backend.academy.loganalyzer.exceptions;

public class ConnectionException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Не удалось подключиться, код ответа: %d";

    public ConnectionException(int code) {
        super(MESSAGE_FORMAT.formatted(code));
    }
}
