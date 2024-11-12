package backend.academy.loganalyzer;

import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.enums.Format;
import backend.academy.loganalyzer.mapper.InputMapper;
import backend.academy.loganalyzer.models.Input;
import backend.academy.loganalyzer.models.Report;
import backend.academy.loganalyzer.parser.InputParser;
import backend.academy.loganalyzer.service.LogAnalyzerService;
import backend.academy.loganalyzer.validators.Validator;
import backend.academy.loganalyzer.writer.ReportFileWriter;
import backend.academy.loganalyzer.writer.impl.ReportAdocWriter;
import backend.academy.loganalyzer.writer.impl.ReportMarkdownWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogAnalyzerApi {
    private final String[] args;

    private final LogAnalyzerService logAnalyzerService;
    private final InputMapper inputMapper;

    public LogAnalyzerApi(String[] args) {
        this.args = args.clone();
        this.logAnalyzerService = new LogAnalyzerService();
        this.inputMapper = new InputMapper();
    }

    public void start() {

        try {
            Input input = InputParser.parse(args);

            long startTime = System.nanoTime(); // замер времени выполнения
            Report report = createReport(input);
            long finishTime = System.nanoTime();
            long duration = (finishTime - startTime) / 1000000;

            log.info("Время выполнения: {}", duration);

            ReportFileWriter formatter = getReportFormatter(report.format());

            formatter.createFile(report, Constants.REPORT_DIRECTORY);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private ReportFileWriter getReportFormatter(Format format) {
        return switch (format) {
            case ADOC -> new ReportAdocWriter();
            case MARKDOWN -> new ReportMarkdownWriter();
        };
    }

    private Report createReport(Input input) {
        return Validator.isUrl(input.globOrUrl())
            ? logAnalyzerService.makeReportFromUrl(inputMapper.inputToUrlInputDTO(input))
            : logAnalyzerService.makeReportFromFile(inputMapper.inputToFileInputDTO(input));
    }
}
