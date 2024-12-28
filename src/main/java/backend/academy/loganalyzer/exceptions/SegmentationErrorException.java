package backend.academy.loganalyzer.exceptions;

public class SegmentationErrorException extends RuntimeException {
    private static final String MESSAGE = "Ошибка сегментации файла";

    public SegmentationErrorException() {
        super(MESSAGE);
    }
}
