package backend.academy.loganalyzer.service;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.analyzer.impl.ListFilesAnalyzer;
import backend.academy.loganalyzer.analyzer.impl.UrlAnalyzer;
import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.dto.FileInputDTO;
import backend.academy.loganalyzer.dto.UrlInputDTO;
import backend.academy.loganalyzer.enums.Filter;
import backend.academy.loganalyzer.enums.Format;
import backend.academy.loganalyzer.exceptions.FilesNotFoundException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.models.Report;
import backend.academy.loganalyzer.parser.PathsParser;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.http.HttpClient;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzerService {
    private static final int SCALE = 3;

    public Report makeReportFromUrl(UrlInputDTO urlInputDTO) {
        HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(Constants.CONNECT_TIMEOUT))
            .build();
        LogDateFilter dateFilter = new LogDateFilter(urlInputDTO.from(), urlInputDTO.to());
        LogFiltration filtration = new LogFiltration(urlInputDTO.filter(), urlInputDTO.filterValue());
        Analyzer analyzer = new UrlAnalyzer(urlInputDTO.url(), filtration, dateFilter, client);

        LogSummary logSummary = analyzer.analyze();

        return createUrlReport(urlInputDTO, logSummary);
    }

    public Report makeReportFromFile(FileInputDTO fileInputDTO) {
        List<Path> files = (new PathsParser(Constants.INPUT_DIRECTORY)).parse(fileInputDTO.glob());
        if (files.isEmpty()) {
            throw new FilesNotFoundException();
        }
        LogDateFilter dateFilter = new LogDateFilter(fileInputDTO.from(), fileInputDTO.to());
        LogFiltration filtration = new LogFiltration(fileInputDTO.filter(), fileInputDTO.filterValue());
        ListFilesAnalyzer listFilesAnalyzer = new ListFilesAnalyzer(files, filtration, dateFilter);

        LogSummary logSummary = listFilesAnalyzer.analyze();

        return createFilesReport(fileInputDTO, logSummary, files);
    }

    private Report createFilesReport(FileInputDTO fileInputDTO, LogSummary logSummary, List<Path> files) {
        return getReport(logSummary, files, fileInputDTO.format(), fileInputDTO.from(), fileInputDTO.to(),
            fileInputDTO.filter(), fileInputDTO.filterValue());
    }

    private Report createUrlReport(UrlInputDTO urlInputDTO, LogSummary logSummary) {
        return getReport(logSummary, null, urlInputDTO.format(), urlInputDTO.from(), urlInputDTO.to(),
            urlInputDTO.filter(),
            urlInputDTO.filterValue());
    }

    private Report getReport(
        LogSummary logSummary,
        List<Path> files,
        Format format,
        LocalDate from,
        LocalDate localDate,
        Filter filter,
        String filterValue
    ) {
        if (logSummary.count() == 0) {
            return new Report(
                files,
                format == null ? Constants.DEFAULT_FORMAT : format,
                from,
                localDate,
                filter,
                filterValue,
                0,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                logSummary.resources(),
                logSummary.codes()
            );
        }
        BigDecimal avg = BigDecimal.valueOf(logSummary.sum() / logSummary.count())
            .setScale(SCALE, RoundingMode.HALF_UP);
        BigDecimal percentile = BigDecimal.valueOf(logSummary.tDigest().quantile(Constants.PERCENTILE_VALUE))
            .setScale(SCALE, RoundingMode.HALF_UP);

        return new Report(
            files,
            format == null ? Constants.DEFAULT_FORMAT : format,
            from,
            localDate,
            filter,
            filterValue,
            logSummary.count(),
            avg,
            percentile,
            sortMap(logSummary.resources()),
            sortMap(logSummary.codes())
        );
    }

    private <K> Map<K, Integer> sortMap(Map<K, Integer> resources) {
        return resources.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
}
