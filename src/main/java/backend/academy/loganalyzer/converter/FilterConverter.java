package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.exceptions.IllegalFilterValueException;
import com.beust.jcommander.IStringConverter;

public class FilterConverter implements IStringConverter<Filter> {
    @Override
    public Filter convert(String title) {
        Filter filter = Filter.valueOfLabel(title);
        if (filter == null) {
            throw new IllegalFilterValueException("Не задано значение фильтрации");
        }
        return filter;
    }
}
