package backend.academy.loganalyzer.exceptions;

public class FilesNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Файлы не найдены";

    public FilesNotFoundException() {
        super(MESSAGE);
    }
}
