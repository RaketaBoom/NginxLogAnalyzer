package backend.academy.loganalyzer.exceptions;

public class MaxRetriesExceededException extends RuntimeException {
    private static final String MESSAGE_FORMAT = """
        Не удалось подключиться к %s
        Превышено максимальное количество попыток подключения
        """;

    public MaxRetriesExceededException(String s) {
        super(MESSAGE_FORMAT.formatted(s));
    }
}
