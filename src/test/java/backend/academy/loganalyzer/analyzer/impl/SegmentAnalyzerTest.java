package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.models.Segment;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;

class SegmentAnalyzerTest {

    @Test
    void testAnalyze_happyPath_returnsLogSummary() {
        // Arrange
        Segment segment = getLogsSegment();
        SegmentAnalyzer segmentAnalyzer = new SegmentAnalyzer(
            segment,
            new LogFiltration(null, null),
            new LogDateFilter(null, null)
        );
        Map<Integer, Integer> expectedCodes = getCodes1();

        // Act
        LogSummary actualLogSummary = segmentAnalyzer.analyze();

        // Assert
        assertThat(actualLogSummary.count()).isEqualTo(10);
        assertThat(actualLogSummary.sum()).isEqualTo(17086.0);
        assertThat(actualLogSummary.tDigest().quantile(Constants.PERCENTILE_VALUE)).isEqualTo(3088, offset(1.0));
        assertThat(actualLogSummary.codes()).containsExactlyInAnyOrderEntriesOf(expectedCodes);
    }

    private Map<Integer, Integer> getCodes1() {
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(200, 7);
        expectedMap.put(301, 2);
        expectedMap.put(302, 1);
        return expectedMap;
    }

    private Segment getLogsSegment() {
        String string =
            """
                39.131.24.228 - - [10/Nov/2024:20:33:41 +0000] "HEAD /Function-based/Configurable/implementation/frame_zero%20tolerance.php HTTP/1.1" 200 1742 "-" "Mozilla/5.0 (X11; Linux x86_64; rv:6.0) Gecko/1973-28-05 Firefox/35.0"
                52.43.138.160 - - [10/Nov/2024:20:33:42 +0000] "GET /Ameliorated/Assimilated-uniform/access_emulation.jpg HTTP/1.1" 302 71 "-" "Mozilla/5.0 (Windows NT 5.01; en-US; rv:1.9.3.20) Gecko/2005-04-04 Firefox/36.0"
                162.12.113.73 - - [10/Nov/2024:20:33:43 +0000] "GET /holistic%20hardware.js HTTP/1.1" 301 57 "-" "Opera/10.34 (X11; Linux x86_64; en-US) Presto/2.10.342 Version/10.00"
                81.247.166.102 - - [10/Nov/2024:20:33:44 +0000] "GET /solution-oriented.js HTTP/1.1" 200 1712 "-" "Mozilla/5.0 (Windows CE; en-US; rv:1.9.3.20) Gecko/2023-27-04 Firefox/35.0"
                113.26.126.66 - - [10/Nov/2024:20:33:45 +0000] "GET /Proactive-solution/Extended.jpg HTTP/1.1" 301 38 "-" "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/531.9.7 (KHTML, like Gecko) Version/5.2 Safari/531.9.7"
                130.92.169.2 - - [10/Nov/2024:20:33:46 +0000] "GET /clear-thinking%20Networked-bottom-line.hmtl HTTP/1.1" 200 2609 "-" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_10) AppleWebKit/5311 (KHTML, like Gecko) Chrome/40.0.889.0 Mobile Safari/5311"
                116.23.166.15 - - [10/Nov/2024:20:33:47 +0000] "GET /eco-centric.js HTTP/1.1" 200 3088 "-" "Mozilla/5.0 (Windows CE) AppleWebKit/5322 (KHTML, like Gecko) Chrome/40.0.802.0 Mobile Safari/5322"
                143.19.139.77 - - [10/Nov/2024:20:34:15 +0000] "GET /Down-sized_product%20benchmark/Fully-configurable%20Inverse.js HTTP/1.1" 200 3084 "-" "Mozilla/5.0 (Windows NT 5.2) AppleWebKit/5350 (KHTML, like Gecko) Chrome/36.0.820.0 Mobile Safari/5350"
                136.5.107.201 - - [10/Nov/2024:20:34:16 +0000] "DELETE /utilisation_Function-based_De-engineered.gif HTTP/1.1" 200 2045 "-" "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_9_3 rv:6.0; en-US) AppleWebKit/536.21.2 (KHTML, like Gecko) Version/6.0 Safari/536.21.2"
                232.182.229.206 - - [10/Nov/2024:20:34:17 +0000] "GET /Ameliorated_bandwidth-monitored.jpg HTTP/1.1" 200 2640 "-" "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_1 like Mac OS X; en-US) AppleWebKit/531.22.6 (KHTML, like Gecko) Version/5.0.5 Mobile/8B117 Safari/6531.22.6\"""";
        ByteBuffer buffer = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
        return new Segment(buffer);
    }

}
