package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.models.Log;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogParserTest {

    @Test
    void testParse_validInput_returnsLog() {
        // Arrange
        String input =
            "39.131.24.228 - - [10/Nov/2024:20:33:41 +0000] \"HEAD /Function-based/Configurable/implementation/frame_zero%20tolerance.php HTTP/1.1\" 200 1742 \"-\" \"Mozilla/5.0 (X11; Linux x86_64; rv:6.0) Gecko/1973-28-05 Firefox/35.0\"";
        Log expectedLog = getLog1();

        // Act
        Log actualLog = LogParser.parse(input);

        //Assert
        assertEquals(expectedLog, actualLog);
    }

    private Log getLog1() {
        return new Log(
            "39.131.24.228",
            OffsetDateTime.of(2024, 11, 10, 20, 33, 41, 0, ZoneOffset.UTC),
            "HEAD",
            "/Function-based/Configurable/implementation/frame_zero%20tolerance.php",
            "HTTP/1.1",
            200,
            1742,
            "-",
            "Mozilla/5.0 (X11; Linux x86_64; rv:6.0) Gecko/1973-28-05 Firefox/35.0"
        );
    }
}
