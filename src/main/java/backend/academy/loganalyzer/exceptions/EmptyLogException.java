package backend.academy.loganalyzer.exceptions;

public class EmptyLogException extends RuntimeException {
    private static final String MESSAGE = "В обработку передан пустой лог";

    public EmptyLogException() {
        super(MESSAGE);
    }
}
