package backend.academy.loganalyzer.validators;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.exceptions.IllegalDateValueException;
import backend.academy.loganalyzer.exceptions.IllegalFilterValueException;
import backend.academy.loganalyzer.exceptions.IllegalPathValueException;
import backend.academy.loganalyzer.exceptions.MissingParameterPathException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.Log;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {
    @Mock
    LogFiltration filtration;
    @Mock
    LogDateFilter dateFilter;

    static Log log;

    @BeforeAll
    static void beforeAll() {
        log = new Log("192.168.1.1", null, "GET", "/downloads/product_1",
            "HTTP/1.1", 304, 1150, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21");
    }

    @Test
    void testIsGlob_glob_true() {
        // Arrange
        String input = "logs/**";

        // Act && Assert
        assertThat(Validator.isGlob(input)).isTrue();
    }

    @Test
    void testIsGlob_notGlob_false() {
        // Arrange
        String input = "logs?+fjwof832????////";

        // Act && Assert
        assertThat(Validator.isGlob(input)).isFalse();
    }

    @Test
    void testIsUrl_url_true() {
        // Arrange
        String input = "https://example.com";

        // Act && Assert
        assertThat(Validator.isUrl(input)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://example.com", "logs/**"})
    void testValidatePath_url_glob_DoesNotThrow(String input) {
        // Act && Assert
        assertDoesNotThrow(() -> Validator.validatePath(input));
    }

    @Test
    void testValidatePath_null_MissingParameterPathException() {
        // Act && Assert
        assertThrows(MissingParameterPathException.class,
            () -> Validator.validatePath(null));
    }

    @Test
    void testValidatePath_noUrlNoGlob_IllegalPathValueException() {
        // Act && Assert
        assertThrows(IllegalPathValueException.class,
            () -> Validator.validatePath("fdsl3&21;"));
    }

    @Test
    void testValidateDates_fromAfterTo_IllegalDateValueException() {
        // Arrange
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2022, 1, 1);

        // Act && Assert
        assertThrows(IllegalDateValueException.class,
            () -> Validator.validateDates(from, to));
    }

    @Test
    void testValidateDates_fromBeforeTo_IllegalDateValueException() {
        // Arrange
        LocalDate from = LocalDate.of(2022, 1, 1);
        LocalDate to = LocalDate.of(2023, 1, 1);

        // Act && Assert
        assertDoesNotThrow(() -> Validator.validateDates(from, to));
    }

    @Test
    void testValidateFilter_nullFilterNotNullValue_IllegalFilterValueException() {
        // Arrange
        String filterValue = "GET";

        // Act && Assert
        assertThrows(IllegalFilterValueException.class,
            () -> Validator.validateFilter(null, filterValue));
    }

    @Test
    void testValidateFilter_notNullFilterNullValue_IllegalFilterValueException() {
        // Arrange
        Filter filter = Filter.RESOURCE;

        // Act && Assert
        assertThrows(IllegalFilterValueException.class,
            () -> Validator.validateFilter(filter, null));
    }

    @Test
    void testIsValidLog_validLog_true() {
        // Arrange
        Mockito.when(filtration.matches(log)).thenReturn(true);
        Mockito.when(dateFilter.isDateInRange(log)).thenReturn(true);

        // Act && Assert
        assertTrue(Validator.isValidLog(log, filtration, dateFilter));
    }

    @Test
    void testIsValidLog_invalidLog_false() {
        // Arrange
        Mockito.when(filtration.matches(log)).thenReturn(true);
        Mockito.when(dateFilter.isDateInRange(log)).thenReturn(false);

        // Act && Assert
        assertFalse(Validator.isValidLog(log, filtration, dateFilter));
    }

}
