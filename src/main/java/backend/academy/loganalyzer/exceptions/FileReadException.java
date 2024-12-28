package backend.academy.loganalyzer.exceptions;

public class FileReadException extends RuntimeException {
    private static final String MESSAGE = "Ошибка чтения файла";

    public FileReadException() {
        super(MESSAGE);
    }
}
