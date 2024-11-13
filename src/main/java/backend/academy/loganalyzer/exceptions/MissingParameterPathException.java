package backend.academy.loganalyzer.exceptions;

public class MissingParameterPathException extends RuntimeException {
    private static final String MESSAGE = "Не указан параметр --path";

    public MissingParameterPathException() {
        super(MESSAGE);
    }
}
