package backend.academy.loganalyzer.exceptions;

public class MultithreadingException extends RuntimeException {
    private static final String MESSAGE = "Ошибка при работе с многопоточностью";

    public MultithreadingException() {
        super(MESSAGE);
    }
}
