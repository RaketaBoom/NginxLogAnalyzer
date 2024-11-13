package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.exceptions.FileReadException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.Log;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.parser.LogParser;
import backend.academy.loganalyzer.validators.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileSingleThreadingAnalyzer implements Analyzer {
    private final Path file;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;

    @Override
    public LogSummary analyze() {
        LogSummary logSummary = new LogSummary();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;

            while ((line = reader.readLine()) != null) {
                Log logModel = LogParser.parse(line);
                if (Validator.isValidLog(logModel, filtration, dateFilter)) {
                    logSummary.addLog(logModel);
                }
            }
        } catch (IOException e) {
            throw new FileReadException();
        }

        return logSummary;
    }
}
