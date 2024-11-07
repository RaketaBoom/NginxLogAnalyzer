package backend.academy.loganalyzer.models;

import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.enums.Format;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record Report(
    List<String> files,
    Format format,
    LocalDateTime from,
    LocalDateTime to,
    Map<Filter, String> filters,
    Integer requestCount,
    Double avgSize,
    Double percentile,
    Map<String, Integer> popularResources,
    Map<Integer, Integer> popularCodes
) {
}
