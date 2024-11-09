package backend.academy.loganalyzer.models;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.enums.Format;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record Report(
    List<String> files,
    Format format,
    LocalDate from,
    LocalDate to,
    Filter filter,
    String filterValue,
    Integer requestCount,
    Double avgSize,
    Double percentile,
    Map<String, Integer> resources,
    Map<Integer, Integer> popularCodes
) {
}
