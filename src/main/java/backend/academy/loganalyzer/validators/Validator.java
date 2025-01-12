package backend.academy.loganalyzer.validators;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.exceptions.IllegalDateValueException;
import backend.academy.loganalyzer.exceptions.IllegalFilterValueException;
import backend.academy.loganalyzer.exceptions.IllegalPathValueException;
import backend.academy.loganalyzer.exceptions.MissingParameterPathException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.Log;
import java.time.LocalDate;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {
    private static final Pattern GLOB_PATTERN = Pattern.compile("^([\\w.-]+/){0,5}[\\w.-]*\\*{0,2}(/?[\\w.-]+){0,5}$");

    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(http|https)://[^\\s/$.?#].[^\\s]*$"
    );

    public static boolean isGlob(String value) {
        return GLOB_PATTERN.matcher(value).matches();
    }

    public static boolean isUrl(String value) {
        return URL_PATTERN.matcher(value).matches();
    }

    public static void validatePath(String value) {
        if (value == null) {
            throw new MissingParameterPathException();
        }
        if (!(isGlob(value) || isUrl(value))) {
            throw new IllegalPathValueException();
        }
    }

    public static void validateDates(LocalDate from, LocalDate to) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalDateValueException("The 'from' date cannot be later than the 'to' date.");
        }
    }

    public static void validateFilter(Filter filter, String value) {
        if (filter == null && value != null) {
            throw new IllegalFilterValueException("Не задано значение фильтрации");
        }
        if (filter != null && value == null) {
            throw new IllegalFilterValueException("Не указан тип фильтрации");
        }
    }

    public static boolean isValidLog(Log logModel, LogFiltration filtration, LogDateFilter dateFilter) {
        return filtration.matches(logModel) && dateFilter.isDateInRange(logModel);
    }
}
