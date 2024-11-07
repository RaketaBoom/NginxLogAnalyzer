package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.exceptions.IllegalFilterValueException;
import com.beust.jcommander.IStringConverter;

public class FilterConverter implements IStringConverter<Filter> {
    @Override
    public Filter convert(String value) {
        return switch (value){
            case "ip" -> Filter.IP;
            case "user" -> Filter.USER;
            case "method" -> Filter.METHOD;
            case "resource" -> Filter.RESOURCE;
            case "code" -> Filter.CODE;
            case "size" -> Filter.SIZE;
            case "referer" -> Filter.REFERER;
            case "agent" -> Filter.AGENT;
            default -> throw new IllegalFilterValueException("Не задано значение фильтрации");
        };
    }
}
