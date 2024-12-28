package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.enums.Format;
import backend.academy.loganalyzer.exceptions.IllegalFormatValueException;
import com.beust.jcommander.IStringConverter;

public class FormatConverter implements IStringConverter<Format> {

    @Override
    public Format convert(String title) {
        Format format = Format.valueOfLabel(title);
        if (format == null) {
            throw new IllegalFormatValueException();
        }
        return format;
    }
}
