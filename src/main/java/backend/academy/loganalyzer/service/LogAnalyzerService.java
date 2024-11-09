package backend.academy.loganalyzer.service;

import backend.academy.loganalyzer.analyzer.UrlAnalyzer;
import backend.academy.loganalyzer.dto.FileInputDTO;
import backend.academy.loganalyzer.dto.UrlInputDTO;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.models.Report;
import backend.academy.loganalyzer.parser.PathsParser;
import java.nio.file.Path;
import java.util.List;
import static backend.academy.loganalyzer.constants.Constants.PERCENTLE_VALUE;

public class LogAnalyzerService {
    public Report makeReportFromUrl(UrlInputDTO urlInputDTO) {
        LogDateFilter dateFilter = new LogDateFilter(urlInputDTO.from(), urlInputDTO.to());
        LogFiltration filtration = new LogFiltration(urlInputDTO.filter(), urlInputDTO.filterValue());
        UrlAnalyzer urlAnalyzer = new UrlAnalyzer(urlInputDTO.url(), filtration, dateFilter);

        LogSummary logSummary = urlAnalyzer.analyze();

        return createUrlReport(urlInputDTO, logSummary);
    }

    public Report makeReportFromFile(FileInputDTO fileInputDTO) {
        List<Path> files;

        files = PathsParser.parse(fileInputDTO.glob());

        return null;

    }

    private Report createUrlReport(UrlInputDTO urlInputDTO, LogSummary logSummary) {
        double avg = logSummary.sum() / logSummary.count();
        double percentile = logSummary.tDigest().quantile(PERCENTLE_VALUE);

        return new Report(
            null,
            urlInputDTO.format(),
            urlInputDTO.from(),
            urlInputDTO.to(),
            urlInputDTO.filter(),
            urlInputDTO.filterValue(),
            logSummary.count(),
            avg,
            percentile,
            logSummary.popularResources(),
            logSummary.popularCodes()
        );
    }
}
