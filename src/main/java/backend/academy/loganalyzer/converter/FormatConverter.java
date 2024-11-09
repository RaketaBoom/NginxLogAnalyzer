package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.enums.Format;
import backend.academy.loganalyzer.exceptions.IllegalFormatValueException;
import com.beust.jcommander.IStringConverter;

public class FormatConverter implements IStringConverter<Format> {

    @Override
    public Format convert(String value) {
        return switch (value) {
            case "adoc" -> Format.ADOC;
            case "markdown" -> Format.MARKDOWN;
            default -> throw new IllegalFormatValueException();
        };
    }
}
