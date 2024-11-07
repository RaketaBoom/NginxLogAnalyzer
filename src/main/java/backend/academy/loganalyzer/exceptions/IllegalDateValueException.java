package backend.academy.loganalyzer.exceptions;

public class IllegalDateValueException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Недопустимое значение даты: %s";
    public IllegalDateValueException(String s) {
        super(MESSAGE_FORMAT.formatted(s));
    }
}
