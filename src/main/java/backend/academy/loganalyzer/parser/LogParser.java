package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.exceptions.LogFormatException;
import backend.academy.loganalyzer.models.Log;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogParser {
    //language=regexp
    private static final String IP_V6 =
        """
            (?:(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|\
            (?:[0-9a-fA-F]{1,4}:){1,7}:|\
            (?:[0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|\
            (?:[0-9a-fA-F]{1,4}:){1,5}(?::[0-9a-fA-F]{1,4}){1,2}|\
            (?:[0-9a-fA-F]{1,4}:){1,4}(?::[0-9a-fA-F]{1,4}){1,3}|\
            (?:[0-9a-fA-F]{1,4}:){1,3}(?::[0-9a-fA-F]{1,4}){1,4}|\
            (?:[0-9a-fA-F]{1,4}:){1,2}(?::[0-9a-fA-F]{1,4}){1,5}|\
            [0-9a-fA-F]{1,4}:(?::[0-9a-fA-F]{1,4}){1,6}|\
            :(?:(?::[0-9a-fA-F]{1,4}){1,7}|:)|\
            fe80:(?::[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|\
            ::(?:ffff(?::0{1,4})?:)?\
            (?:(?:25[0-5]|(?:2[0-4]|1?\\d)?\\d)\\.){3}\
            (?:25[0-5]|(?:2[0-4]|1?\\d)?\\d)|\
            (?:[0-9a-fA-F]{1,4}:){1,4}:\
            (?:(?:25[0-5]|(?:2[0-4]|1?\\d)?\\d)\\.){3}\
            (?:25[0-5]|(?:2[0-4]|1?\\d)?\\d))\
            """;
    //language=regexp
    private static final String IP_V4 = "(?:\\d+\\.?){4}";
    //language=regexp
    private static final String IP_REGEXP = "^(" + IP_V4 + "|" + IP_V6 + ") ";
    //language=regexp
    private static final String USER_REGEXP = "- - ";
    //language=regexp
    private static final String TIMESTAMP_REGEXP = "\\[(.*)] ";
    //language=regexp
    private static final String METHOD_REGEXP = "\"(GET|PUT|PATCH|HEAD|DELETE|POST) ";
    //language=regexp
    private static final String RESOURCE_REGEXP = "(.*?) ";
    //language=regexp
    private static final String PROTOCOL_REGEXP = "(.*?)\" ";
    //language=regexp
    private static final String CODE_REGEXP = "([1-5]\\d\\d) ";
    //language=regexp
    private static final String SIZE_REGEXP = "(\\d+) ";
    //language=regexp
    private static final String REFERER_REGEXP = "\"(.*?)\" ";
    //language=regexp
    private static final String AGENT_REGEXP = "\"(.*?)\"$";

    //language=regexp
    private static final String LOG_REGEXP =
        IP_REGEXP + USER_REGEXP + TIMESTAMP_REGEXP + METHOD_REGEXP + RESOURCE_REGEXP + PROTOCOL_REGEXP +
        CODE_REGEXP + SIZE_REGEXP + REFERER_REGEXP + AGENT_REGEXP;

    private static final Pattern PATTERN = Pattern.compile(LOG_REGEXP);
    private static final String DATE_PATTERN = "dd/MMM/yyyy:HH:mm:ss Z";

    public static Log parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new LogFormatException();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);

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
