package backend.academy.loganalyzer.models;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SegmentTest {

    @Test
    void testReadLine_text_StringLines() {
        // Arrange
        String string = getStringLogs();
        ByteBuffer buffer = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
        Segment segment = new Segment(buffer);

        // Act && Assert
        assertThat(segment.readLine()).isNotNull();
        assertThat(segment.readLine()).isNotNull();
        assertThat(segment.readLine()).isEqualTo("162.12.113.73 - - [10/Nov/2024:20:33:43 +0000] \"GET /holistic%20hardware.js HTTP/1.1\" 301 57 \"-\" \"Opera/10.34 (X11; Linux x86_64; en-US) Presto/2.10.342 Version/10.00\"");
        assertThat(segment.readLine()).isNull();
    }

    @Test
    void testReadLine_emptyBuffer_null() {
        // Arrange
        String string = "";
        ByteBuffer buffer = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
        Segment segment = new Segment(buffer);

        // Act && Assert
        assertThat(segment.readLine()).isNull();
        assertThat(segment.readLine()).isNull();
        assertThat(segment.readLine()).isNull();
    }

    private String getStringLogs() {
        return """
            39.131.24.228 - - [10/Nov/2024:20:33:41 +0000] "HEAD /Function-based/Configurable/implementation/frame_zero%20tolerance.php HTTP/1.1" 200 1742 "-" "Mozilla/5.0 (X11; Linux x86_64; rv:6.0) Gecko/1973-28-05 Firefox/35.0"
            52.43.138.160 - - [10/Nov/2024:20:33:42 +0000] "GET /Ameliorated/Assimilated-uniform/access_emulation.jpg HTTP/1.1" 302 71 "-" "Mozilla/5.0 (Windows NT 5.01; en-US; rv:1.9.3.20) Gecko/2005-04-04 Firefox/36.0"
            162.12.113.73 - - [10/Nov/2024:20:33:43 +0000] "GET /holistic%20hardware.js HTTP/1.1" 301 57 "-" "Opera/10.34 (X11; Linux x86_64; en-US) Presto/2.10.342 Version/10.00"\
            """;
    }
}
