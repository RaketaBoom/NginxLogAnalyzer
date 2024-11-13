package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.exceptions.MultithreadingException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.models.Segment;
import backend.academy.loganalyzer.spliterator.SegmentSpliterator;
import backend.academy.loganalyzer.utils.LogSummaryMerger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileMultithreadingAnalyzer implements Analyzer {
    private final Path file;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;

    @Override
    public LogSummary analyze() {
        SegmentSpliterator spliterator = getSegmentSpliterator();

        List<Segment> segments = spliterator.split();

        List<LogSummary> logSummaries = new ArrayList<>();
        try (ExecutorService executor = Executors.newFixedThreadPool(spliterator.numberOfSegments())) {

            List<Future<LogSummary>> futures = new ArrayList<>();
            for (Segment segment : segments) {
                futures.add(executor.submit(() ->
                    new SegmentAnalyzer(segment, filtration, dateFilter).analyze()
                ));
            }

            for (Future<LogSummary> logFuture : futures) {
                try {
                    logSummaries.add(logFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                    throw new MultithreadingException();
                }
            }
        }

        return LogSummaryMerger.merge(logSummaries);
    }

    private SegmentSpliterator getSegmentSpliterator() {
        return new SegmentSpliterator(file, Runtime.getRuntime().availableProcessors());
    }
}
