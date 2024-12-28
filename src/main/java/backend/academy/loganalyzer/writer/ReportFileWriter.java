package backend.academy.loganalyzer.writer;

import backend.academy.loganalyzer.models.Report;

public interface ReportFileWriter {
    void createFile(Report report, String path);
}
