package backend.academy.loganalyzer.filtration;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.models.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogFiltrationTest {
    static Log log;

    @BeforeAll
    static void beforeAll() {
        log = new Log("192.168.1.1", null, "GET", "/downloads/product_1",
            "HTTP/1.1", 304, 1150, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21");
    }

    @Test
    void testMatches_ip_true() {
        // Arrange
        LogFiltration filtration = new LogFiltration(Filter.IP, "192.168.1.*");

        // Act && Assert
        assertTrue(filtration.matches(log));
    }

    @Test
    void testMatches_method_true() {
        // Arrange
        LogFiltration filtration = new LogFiltration(Filter.METHOD, "GET");

        // Act && Assert
        assertTrue(filtration.matches(log));
    }

    @Test
    void testMatches_resource_true() {
        // Arrange
        LogFiltration filtration = new LogFiltration(Filter.RESOURCE, ".*product.*");

        // Act && Assert
        assertTrue(filtration.matches(log));
    }

    @Test
    void testMatches_code_true() {
        // Arrange
        LogFiltration filtration = new LogFiltration(Filter.CODE, "304");

        // Act && Assert
        assertTrue(filtration.matches(log));
    }

    @Test
    void testMatches_code_false1() {
        // Arrange
        LogFiltration filtration = new LogFiltration(Filter.CODE, "404");

        // Act && Assert
        assertFalse(filtration.matches(log));
    }

    @Test
    void testMatches_code_false2() {
        // Arrange
        LogFiltration filtration = new LogFiltration(Filter.CODE, "bulba");

        // Act && Assert
        assertFalse(filtration.matches(log));
    }
}
