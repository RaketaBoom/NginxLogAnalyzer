package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.LogSummary;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;

class FileSingleThreadingAnalyzerTest {
    @Test
    void testAnalyze_log_test1_LogSummary() {
        // Arrange
        FileSingleThreadingAnalyzer analyzer = new FileSingleThreadingAnalyzer(
            Path.of("src/test/resources/logs/log_test1.txt"),
            new LogFiltration(null, null),
            new LogDateFilter(null, null)
        );
        Map<Integer, Integer> expectedCodes = getCodes1();

        // Act
        LogSummary actualLogSummary = analyzer.analyze();

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
}
