package backend.academy.loganalyzer.models;

import backend.academy.loganalyzer.exceptions.EmptyLogException;
import backend.academy.loganalyzer.exceptions.EmptyLogSummaryException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LogSummaryTest {
    LogSummary logSummary;

    @BeforeEach
    void beforeEach() {
        logSummary = new LogSummary();

    }

    @Test
    void testAddLog_nullLog_EmptyLogException(){
        // Act && Assert
        assertThrows(EmptyLogException.class,
            () -> logSummary.addLog(null));
    }

    @Test
    void testAdd_nullLogSummary_EmptyLogSummaryException(){
        // Act && Assert
        assertThrows(EmptyLogSummaryException.class,
            () -> logSummary.add(null));
    }

    @Test
    void testAddLog_Log_LogSummary(){
        //Arrange
        Log log = new Log("192.168.1.1", null, "GET", "/downloads/product_1",
            "HTTP/1.1", 304, 1150, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21");

        // Act
        LogSummary actualLogSummary = logSummary.addLog(log);

        // Assert
        assertThat(actualLogSummary.count()).isEqualTo(1);
        assertThat(actualLogSummary.sum()).isEqualTo(1150);
    }

}
