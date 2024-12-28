package backend.academy.loganalyzer.exceptions;

public class IllegalFormatValueException extends RuntimeException {
    private static final String MESSAGE = "Недопустимое значение формата";

    public IllegalFormatValueException() {
        super(MESSAGE);
    }
}
