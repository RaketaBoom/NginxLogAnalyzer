package backend.academy;

import backend.academy.loganalyzer.parser.InputParser;
import backend.academy.loganalyzer.parser.LogParser;
import lombok.experimental.UtilityClass;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        String logLine = "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"";

        System.out.println(LogParser.parse(logLine));
    }
}
