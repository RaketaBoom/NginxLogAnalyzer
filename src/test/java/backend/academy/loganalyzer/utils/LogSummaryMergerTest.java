package backend.academy.loganalyzer.utils;

import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.models.LogSummary;
import com.tdunning.math.stats.TDigest;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LogSummaryMergerTest {
    @Test
    void testMerge_ListLogSummary_LogSummary() {
        // Arrange
        List<LogSummary> logSummaries = getLogSummaries();

        // Act
        LogSummary actualLogSummary = LogSummaryMerger.merge(logSummaries);

        //Assert
        assertThat(actualLogSummary.count()).isEqualTo(7);
        assertThat(actualLogSummary.sum()).isEqualTo(15);
    }

    private List<LogSummary> getLogSummaries() {
        return List.of(
            new LogSummary(3, 10, TDigest.createDigest(Constants.COMPRESSION_FACTOR), new HashMap<>(), new HashMap<>()),
            new LogSummary(4, 5, TDigest.createDigest(Constants.COMPRESSION_FACTOR), new HashMap<>(), new HashMap<>())
        );
    }
}
