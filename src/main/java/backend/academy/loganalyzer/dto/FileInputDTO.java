package backend.academy.loganalyzer.dto;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.enums.Format;
import java.time.LocalDate;

public record FileInputDTO(
    String glob,
    LocalDate from,
    LocalDate to,
    Format format,
    Filter filter,
    String filterValue
) {
}
