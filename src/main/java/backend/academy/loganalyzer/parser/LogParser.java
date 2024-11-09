package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.exceptions.LogFormatException;
import backend.academy.loganalyzer.models.Log;
import lombok.experimental.UtilityClass;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class LogParser {
    private static final Pattern PATTERN = Pattern.compile(
        "^((?:\\d+\\.?){4}) - - \\[(.*)] \"(GET|PUT|PATCH|HEAD|DELETE|POST)\\s(.*?)\\s(.*?)\" ([1-5][0-9][0-9]) (\\d+) \"(.*?)\" \"(.*?)\"$"
    );

    public static Log parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if(!matcher.matches()) throw new LogFormatException();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

        String ip = matcher.group(1);
        String timestamp = matcher.group(2);
        String method = matcher.group(3);
        String resource = matcher.group(4);
        String protocol = matcher.group(5);
        String code = matcher.group(6);
        String size = matcher.group(7);
        String referer = matcher.group(8);
        String agent = matcher.group(9);

        return new Log(
            ip,
            OffsetDateTime.parse(timestamp, formatter),
            method,
            resource,
            protocol,
            Integer.parseInt(code),
            Integer.parseInt(size),
            referer,
            agent
        );
    }
}
