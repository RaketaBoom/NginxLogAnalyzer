package backend.academy.loganalyzer.exceptions;

public class PathsParserException extends RuntimeException {
    private static final String MESSAGE = "Ошибка парсинга путей к файлам";

    public PathsParserException() {
        super(MESSAGE);
    }
}
