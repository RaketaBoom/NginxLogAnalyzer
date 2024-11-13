package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.Log;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.models.Segment;
import backend.academy.loganalyzer.parser.LogParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentAnalyzer implements Analyzer {
    private final Segment segment;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;

    @Override
    public LogSummary analyze() {
        LogSummary logSummary = new LogSummary();
        String line;
        while ((line = segment.readLine()) != null) {
            Log logModel = LogParser.parse(line);
            if (isValidLog(logModel)) {
                logSummary.addLog(logModel);
            }
        }
        return logSummary;
    }

    private boolean isValidLog(Log logModel) {
        return filtration.matches(logModel) && dateFilter.isDateInRange(logModel);
    }
}