package backend.academy.loganalyzer.filtration;

import backend.academy.loganalyzer.models.Log;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class LogDateFilterTest {
    private static LogDateFilter logDateFilter;

    @Mock
    private Log log;

    @BeforeAll
    static void beforeAll() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);
        logDateFilter = new LogDateFilter(from, to);
    }

    @Test
    void test_log_date_within_range_returns_true() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.parse("2023-06-01T00:00:00+01:00");
        Mockito.when(log.timestamp()).thenReturn(timestamp);

        // Act
        boolean result = logDateFilter.isDateInRange(log);

        // Assert
        assertTrue(result);
    }

    @Test
    void test_log_date_on_from_date_returns_true() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.parse("2023-01-01T00:00:00+01:00");
        Mockito.when(log.timestamp()).thenReturn(timestamp);

        // Act
        boolean result = logDateFilter.isDateInRange(log);

        // Assert
        assertTrue(result);
    }

    @Test
    void test_log_date_before_from_date_returns_false() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.parse("2022-12-31T23:59:59+01:00");
        Mockito.when(log.timestamp()).thenReturn(timestamp);

        // Act
        boolean result = logDateFilter.isDateInRange(log);

        // Assert
        assertFalse(result);
    }

    @Test
    void test_log_date_after_to_date_returns_false() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.parse("2024-01-01T00:00:00+01:00");
        Mockito.when(log.timestamp()).thenReturn(timestamp);

        // Act
        boolean result = logDateFilter.isDateInRange(log);

        // Assert
        assertFalse(result);
    }
}
