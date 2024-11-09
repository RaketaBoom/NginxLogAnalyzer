package backend.academy.loganalyzer.exceptions;

public class FileWriteException extends RuntimeException {
    private static final String MESSAGE = "Ошибка парсинга путей к файлам";

    public FileWriteException() {
        super(MESSAGE);
    }
}
