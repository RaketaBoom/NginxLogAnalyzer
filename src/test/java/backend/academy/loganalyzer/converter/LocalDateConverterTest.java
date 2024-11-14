package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.exceptions.UnformattedDateException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalDateConverterTest {
    @Test
    void testConvert_validDateString_LocalDate() {
        // Arrange
        LocalDateConverter converter = new LocalDateConverter();
        String dateString = "2023-10-05";

        // Act
        LocalDate result = converter.convert(dateString);

        // Assert
        assertEquals(LocalDate.of(2023, 10, 5), result);
    }

    @Test
    void testConvert_emptyString_throwsUnformattedDateException() {
        // Arrange
        LocalDateConverter converter = new LocalDateConverter();
        String dateString = "";

        // Act & Assert
        assertThrows(UnformattedDateException.class, () -> converter.convert(dateString));
    }
}
