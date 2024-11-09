package backend.academy.loganalyzer;

import backend.academy.loganalyzer.dto.FileInputDTO;
import backend.academy.loganalyzer.dto.UrlInputDTO;
import backend.academy.loganalyzer.enums.Format;
import backend.academy.loganalyzer.formatter.ReportFormatter;
import backend.academy.loganalyzer.formatter.impl.AdocFormatter;
import backend.academy.loganalyzer.formatter.impl.MarkdownFormatter;
import backend.academy.loganalyzer.mapper.InputMapper;
import backend.academy.loganalyzer.models.Input;
import backend.academy.loganalyzer.models.Report;
import backend.academy.loganalyzer.parser.InputParser;
import backend.academy.loganalyzer.service.LogAnalyzerService;
import backend.academy.loganalyzer.validators.Validator;
import org.mapstruct.factory.Mappers;
import static backend.academy.loganalyzer.constants.Constants.PATH;

public class LogAnalyzerApi {
    private final String[] args;

    private final LogAnalyzerService logAnalyzerService;
    private final InputMapper inputMapper;

    public LogAnalyzerApi(String[] args) {
        this.args = args.clone();
        this.logAnalyzerService = new LogAnalyzerService();
        this.inputMapper = Mappers.getMapper(InputMapper.class);
    }

    public void start() {
        Input input = InputParser.parse(args);

        Report report = createReport(input);

        ReportFormatter formatter = getReportFormatter(report.format());

        formatter.format(report, PATH);
    }

    private ReportFormatter getReportFormatter(Format format) {
        return switch (format) {
            case ADOC -> new AdocFormatter();
            case MARKDOWN -> new MarkdownFormatter();
        };
    }

    private Report createReport(Input input) {
        if (Validator.isUrl(input.globOrUrl())) {
            UrlInputDTO urlInputDTO = inputMapper.inputToUrlInputDTO(input);
            return logAnalyzerService.makeReportFromUrl(urlInputDTO);
        }
        FileInputDTO fileInputDTO = inputMapper.inputToFileInputDTO(input);
        return logAnalyzerService.makeReportFromFile(fileInputDTO);
    }
}
