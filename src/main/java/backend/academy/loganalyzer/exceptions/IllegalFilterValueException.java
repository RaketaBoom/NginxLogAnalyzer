package backend.academy.loganalyzer.exceptions;

public class IllegalFilterValueException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Недопустимое значение фильтра: %s";

    public IllegalFilterValueException(String s) {
        super(MESSAGE_FORMAT.formatted(s));
    }
}
