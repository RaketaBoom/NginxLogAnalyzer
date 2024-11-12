package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.exceptions.LogFormatException;
import backend.academy.loganalyzer.models.Log;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogParser {
    private static final String DATE_PATTERN = "dd/MMM/yyyy:HH:mm:ss Z";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);

    public static Log parse(String line) {
        int begin = 0;
        int end;

        String ip = null;
        String timestamp = null;
        String method = null;
        String resource = null;
        String protocol = null;
        String code = null;
        String size = null;
        String referer = null;
        String agent = null;
        try {
            end = endOfIp(line, begin);
            ip = line.substring(begin, end);

            begin = beginOfTimeStamp(line, end);
            end = endOfTimeStamp(line, begin);
            timestamp = line.substring(begin, end);

            begin = beginOfMethod(line, end);
            end = endOfMethod(line, begin);
            method = line.substring(begin, end);

            begin = beginOfResource(end);
            end = endOfResource(line, begin);
            resource = line.substring(begin, end);

            begin = beginOfProtocol(end);
            end = endOfProtocol(line, begin);
            protocol = line.substring(begin, end);

            begin = beginOfCode(end);
            end = endOfCode(line, begin);
            code = line.substring(begin, end);

            begin = beginOfSize(end);
            end = endOfSize(line, begin);
            size = line.substring(begin, end);

            begin = beginOfReferer(end);
            end = endOfReferer(line, begin);
            referer = line.substring(begin, end);

            begin = beginOfAgent(end);
            end = endOfAgent(line, begin);
            agent = line.substring(begin, end);
        } catch (StringIndexOutOfBoundsException e) {
            throw new LogFormatException();
        }

        return new Log(
            ip,
            OffsetDateTime.parse(timestamp, FORMATTER),
            method,
            resource,
            protocol,
            Integer.parseInt(code),
            Integer.parseInt(size),
            referer,
            agent
        );
    }

    private static int endOfIp(String line, int iterator) {
        return indexSymbol(line, iterator, ' ');
    }

    private static int beginOfTimeStamp(String line, int iterator) {
        return indexSymbol(line, iterator, '[') + 1;
    }

    private static int endOfTimeStamp(String line, int iterator) {
        return indexSymbol(line, iterator, ']');
    }

    private static int beginOfMethod(String line, int iterator) {
        return indexSymbol(line, iterator, '"') + 1;
    }

    private static int endOfMethod(String line, int iterator) {
        return indexSymbol(line, iterator, ' ');
    }

    private static int beginOfResource(int iterator) {
        return passOneSymbol(iterator);
    }

    private static int endOfResource(String line, int iterator) {
        return indexSymbol(line, iterator, ' ');
    }

    private static int beginOfProtocol(int iterator) {
        return passOneSymbol(iterator);
    }

    private static int endOfProtocol(String line, int iterator) {
        return indexSymbol(line, iterator, '"');
    }

    private static int beginOfCode(int iterator) {
        return passTwoSymbols(iterator);
    }

    private static int endOfCode(String line, int iterator) {
        return indexSymbol(line, iterator, ' ');
    }

    private static int beginOfSize(int iterator) {
        return passOneSymbol(iterator);
    }

    private static int endOfSize(String line, int iterator) {
        return indexSymbol(line, iterator, ' ');
    }

    private static int beginOfReferer(int iterator) {
        return passTwoSymbols(iterator);
    }

    private static int endOfReferer(String line, int iterator) {
        return indexSymbol(line, iterator, '"');
    }

    private static int beginOfAgent(int iterator) {
        return passThreeSymbols(iterator);
    }

    private static int endOfAgent(String line, int iterator) {
        return indexSymbol(line, iterator, '"');
    }

    private static int passOneSymbol(int i) {
        i++;
        return i;
    }

    private static int passTwoSymbols(int i) {
        i++;
        i++;
        return i;
    }

    private static int passThreeSymbols(int i) {
        i++;
        i++;
        i++;
        return i;
    }

    private static int indexSymbol(String line, int iterator, char t) {
        while (line.charAt(iterator) != t) {
            iterator++;
        }
        return iterator;
    }
}
