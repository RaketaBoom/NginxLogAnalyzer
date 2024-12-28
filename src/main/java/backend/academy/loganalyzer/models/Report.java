package backend.academy.loganalyzer.models;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.enums.Format;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SuppressWarnings("RecordComponentNumber")
public record Report(
    List<Path> files,
    Format format,
    LocalDate from,
    LocalDate to,
    Filter filter,
    String filterValue,
    Integer requestCount,
    BigDecimal avgSize,
    BigDecimal percentile,
    Map<String, Integer> resources,
    Map<Integer, Integer> popularCodes
) {
}
