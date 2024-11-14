package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.models.Input;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class InputParserTest {
    @Test
    void testParse_rightArgs1_Input() {
        // Arrange
        String[] args = {"--path", "https://example.com"};

        // Act
        Input actualInput = InputParser.parse(args);

        // Assert
        assertThat(actualInput.globOrUrl()).isEqualTo("https://example.com");
        assertThat(actualInput.from()).isNull();
        assertThat(actualInput.to()).isNull();
        assertThat(actualInput.filter()).isNull();
        assertThat(actualInput.filterValue()).isNull();
    }

    @Test
    void testParse_rightArgs2_Input() {
        // Arrange
        String[] args = {
            "--path", "https://example.com",
            "--from", "2023-12-01",
            "--to", "2024-11-14",
            "--filter-field", "method",
            "--filter-value", "GET"
        };

        // Act
        Input actualInput = InputParser.parse(args);

        // Assert
        assertThat(actualInput.globOrUrl()).isEqualTo("https://example.com");
        assertThat(actualInput.from()).isEqualTo(LocalDate.of(2023, 12, 1));
        assertThat(actualInput.to()).isEqualTo(LocalDate.of(2024, 11, 14));
        assertThat(actualInput.filter()).isEqualTo(Filter.METHOD);
        assertThat(actualInput.filterValue()).isEqualTo("GET");
    }
}
