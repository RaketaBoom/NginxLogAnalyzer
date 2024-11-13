package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.exceptions.MultithreadingException;
import backend.academy.loganalyzer.exceptions.SegmentationErrorException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.models.Segment;
import backend.academy.loganalyzer.spliterator.SegmentSpliterator;
import backend.academy.loganalyzer.utils.LogSummaryMerger;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FileMultithreadingAnalyzer implements Analyzer {
    private final Path file;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;

    @Override
    public LogSummary analyze() {
        int activeThreads = Runtime.getRuntime().availableProcessors();
        SegmentSpliterator spliterator = new SegmentSpliterator(file, activeThreads);
        List<Segment> segments;
        try {
            segments = spliterator.split();
        } catch (IOException e) {
            throw new SegmentationErrorException();
        }

        List<LogSummary> logSummaries = new ArrayList<>();

        try (ExecutorService executor = Executors.newFixedThreadPool(spliterator.numberOfSegments())) {
            List<Future<LogSummary>> futures = new ArrayList<>();

            for (Segment segment : segments) {
                futures.add(executor.submit(() -> {
                    SegmentAnalyzer analyzer = new SegmentAnalyzer(segment, filtration, dateFilter);
                    return analyzer.analyze();
                }));
            }

            for (Future<LogSummary> future : futures) {
                try {
                    logSummaries.add(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                    throw new MultithreadingException();
                }
            }
            executor.shutdown();
        }

        return LogSummaryMerger.merge(logSummaries);
    }
}
