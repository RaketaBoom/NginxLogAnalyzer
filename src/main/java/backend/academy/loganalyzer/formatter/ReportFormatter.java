package backend.academy.loganalyzer.formatter;

import backend.academy.loganalyzer.models.Report;

public interface ReportFormatter {
    void format(Report report, String path);
}
