package backend.academy.loganalyzer.filtration;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.models.Log;
import java.util.regex.Pattern;

public class LogFiltration {
    private final Filter filter;
    private final String value;
    private final Pattern pattern;

    public LogFiltration(Filter filter, String value) {
        this.filter = filter;
        this.value = value;
        this.pattern = (value != null) ? Pattern.compile(value.replace("*", ".*")) : null;
    }

    public boolean matches(Log log) {
        return switch (filter) {
            case IP -> pattern.matcher(log.ip()).matches();
            case METHOD -> pattern.matcher(log.method()).matches();
            case RESOURCE -> pattern.matcher(log.resource()).matches();
            case PROTOCOL -> pattern.matcher(log.protocol()).matches();
            case CODE -> log.code().equals(Integer.parseInt(value));
            case SIZE -> log.size().equals(Integer.parseInt(value));
            case REFERER -> pattern.matcher(log.referer()).matches();
            case AGENT -> pattern.matcher(log.agent()).matches();
            case null -> true;
        };
    }
}
