package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.exceptions.UnformattedDateException;
import com.beust.jcommander.IStringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements IStringConverter<LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate convert(String value) {
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception ex) {
            throw new UnformattedDateException();
        }
    }
}
