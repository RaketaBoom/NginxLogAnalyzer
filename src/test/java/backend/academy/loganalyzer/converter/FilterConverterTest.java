package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.exceptions.IllegalFilterValueException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilterConverterTest {

    @Test
    void testConvert_ipString_ipFilter() {
        // Arrange
        FilterConverter filterConverter = new FilterConverter();

        // Act
        Filter actualFilter = filterConverter.convert("ip");

        // Assert
        assertEquals(Filter.IP, actualFilter);
    }

    @Test
    void testConvert_emptyString_throwsIllegalFilterValueException() {
        // Arrange
        FilterConverter filterConverter = new FilterConverter();

        // Act & Assert
        assertThrows(IllegalFilterValueException.class, () -> filterConverter.convert(""));
    }
}
