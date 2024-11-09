package backend.academy.loganalyzer.models;

import backend.academy.loganalyzer.converter.FilterConverter;
import backend.academy.loganalyzer.converter.FormatConverter;
import backend.academy.loganalyzer.converter.LocalDateConverter;
import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.enums.Format;
import com.beust.jcommander.Parameter;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Input {
    @Parameter(names = "--path", description = "glob or url")
    private String globOrUrl;

    @Parameter(names = "--from", converter = LocalDateConverter.class, description = "from date")
    private LocalDate from;

    @Parameter(names = "--to", converter = LocalDateConverter.class, description = "to date")
    private LocalDate to;

    @Parameter(names = "--format", converter = FormatConverter.class, description = "format")
    private Format format;

    @Parameter(names = "--filter-field", converter = FilterConverter.class, description = "filter-field")
    private Filter filter;

    @Parameter(names = "--filter-value", description = "filter-value")
    private String filterValue;

    public String getGlobOrUrl() {
        return globOrUrl;
    }
}
