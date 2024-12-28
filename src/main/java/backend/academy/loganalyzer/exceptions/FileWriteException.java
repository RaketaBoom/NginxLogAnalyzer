package backend.academy.loganalyzer.exceptions;

public class FileWriteException extends RuntimeException {
    private static final String MESSAGE = "Ошибка записи в файл";

    public FileWriteException() {
        super(MESSAGE);
    }
}
