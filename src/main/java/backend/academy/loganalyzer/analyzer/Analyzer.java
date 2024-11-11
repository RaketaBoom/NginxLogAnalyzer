package backend.academy.loganalyzer.analyzer;

import backend.academy.loganalyzer.models.LogSummary;

public interface Analyzer {
    LogSummary analyze();
}
