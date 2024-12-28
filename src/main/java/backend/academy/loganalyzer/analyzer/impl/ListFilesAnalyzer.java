package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.utils.LogSummaryMerger;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListFilesAnalyzer implements Analyzer {
    private final List<Path> files;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;

    @Override
    public LogSummary analyze() {
        return LogSummaryMerger.merge(files.stream()
            .map(this::fileAnalyze)
            .toList());
    }

    private LogSummary fileAnalyze(Path file) {
        return (Constants.MULTITHREADING_MODE
            ? new FileMultithreadingAnalyzer(file, filtration, dateFilter)
            : new FileSingleThreadingAnalyzer(file, filtration, dateFilter)
        ).analyze();
    }
}
