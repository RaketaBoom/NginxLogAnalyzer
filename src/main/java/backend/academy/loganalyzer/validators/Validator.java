package backend.academy.loganalyzer.validators;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.exceptions.IllegalDateValueException;
import backend.academy.loganalyzer.exceptions.IllegalFilterValueException;
import backend.academy.loganalyzer.exceptions.IllegalPathValueException;
import backend.academy.loganalyzer.models.Input;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern GLOB_PATTERN = Pattern.compile(
        "^([\\w\\-.]+/)*[\\w\\-.]*([*]|[*][*](/[\\w\\-.]+)*)?$"
    );

    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(http|https)://[^\\s/$.?#].[^\\s]*$" // Простой паттерн для URL
    );

    public static boolean isGlob(String value) {
        return GLOB_PATTERN.matcher(value).matches();
    }

    public static boolean isUrl(String value) {
        return URL_PATTERN.matcher(value).matches();
    }

    public static void validatePath(String value){
        if(!(isGlob(value) || isUrl(value))){
            throw new IllegalPathValueException();
        }
    }

    public static void validateDates(LocalDate from, LocalDate to) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalDateValueException("The 'from' date cannot be later than the 'to' date.");
        }
    }

    public static void validateFilter(Filter filter, String value) {
        if (filter == null && value != null){
            throw new IllegalFilterValueException("Не задано значение фильтрации");
        }
        if (filter != null && value == null){
            throw new IllegalFilterValueException("Не указан тип фильтрации");
        }
    }
}
