package backend.academy.loganalyzer.converter;

import backend.academy.loganalyzer.enums.Format;
import backend.academy.loganalyzer.exceptions.IllegalFormatValueException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormatConverterTest {
    @Test
    void testConvert_adocString_adocFormat() {
        // Arrange
        FormatConverter converter = new FormatConverter();
        String input = "adoc";

        // Act
        Format result = converter.convert(input);

        // Assert
        assertEquals(Format.ADOC, result);
    }

    @Test
    void testConvert_markdownString_markdownFormat() {
        // Arrange
        FormatConverter converter = new FormatConverter();
        String input = "markdown";

        // Act
        Format result = converter.convert(input);

        // Assert
        assertEquals(Format.MARKDOWN, result);
    }

    @Test
    void testConvert_emptyString_throwsIllegalFormatValueException() {
        // Arrange
        FormatConverter converter = new FormatConverter();
        String input = "";

        // Act & Assert
        assertThrows(IllegalFormatValueException.class, () -> converter.convert(input));
    }
}
