package backend.academy.loganalyzer.exceptions;

public class IllegalPathValueException extends RuntimeException {
    private static final String MESSAGE = "Недопустимое значение для --path";

    public IllegalPathValueException() {
        super(MESSAGE);
    }
}
