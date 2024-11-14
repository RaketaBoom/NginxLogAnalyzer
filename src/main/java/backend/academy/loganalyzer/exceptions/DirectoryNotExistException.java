package backend.academy.loganalyzer.exceptions;

public class DirectoryNotExistException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Каталог не найден: %s";

    public DirectoryNotExistException(String directory) {
        super(MESSAGE_FORMAT.formatted(directory));
    }
}
