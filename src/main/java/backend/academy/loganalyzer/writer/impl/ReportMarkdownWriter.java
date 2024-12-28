package backend.academy.loganalyzer.writer.impl;

import backend.academy.loganalyzer.exceptions.FileWriteException;
import backend.academy.loganalyzer.httpcodes.HttpStatusCodeMapper;
import backend.academy.loganalyzer.models.Report;
import backend.academy.loganalyzer.writer.ReportFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReportMarkdownWriter implements ReportFileWriter {
    private static final String TABLE_BORDER = "\n\n";
    private static final String END_TABLE_LINE = " |\n";
    private static final String FIELD_BORDER = " | ";

    @Override
    public void createFile(Report report, String path) {
        StringBuilder markdown = new StringBuilder();

        markdown.append(files(report));
        markdown.append(parameters(report));
        markdown.append(statistic(report));
        markdown.append(resources(report));
        markdown.append(codes(report));

        try {
            Files.writeString(
                Path.of(path, "report.md"),
                markdown.toString(),
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            throw new FileWriteException();
        }
    }

    private StringBuilder parameters(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("## Параметры\n\n")
            .append("| Параметр       | Значение |\n")
            .append("|----------------|----------|\n")
            .append("| Формат         | ").append(report.format()).append(END_TABLE_LINE)
            .append(report.from() != null ? "| Начальная дата | " + report.from() + "\n" : "")
            .append(report.to() != null ? "| Конечная дата | " + report.to() + "\n" : "")
            .append(report.filter() != null ? "| Фильтр | " + report.filter() + "\n" : "")
            .append(report.filterValue() != null ? "| Значение фильтра | " + report.filterValue() + TABLE_BORDER
                : TABLE_BORDER);
        return str;
    }

    private StringBuilder files(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("## Файлы\n\n")
            .append("| Название         | Ссылка |\n")
            .append("|------------------|--------|\n");
        if (report.files() == null) {
            str.append(TABLE_BORDER);
            return str;
        }
        report.files()
            .forEach(x -> str
                .append("| ").append(x.getFileName()).append(FIELD_BORDER)
                .append(" [").append(x).append("]")
                .append("(").append(x).append(")")
                .append("\n"));
        str.append(TABLE_BORDER);
        return str;
    }

    private StringBuilder statistic(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("## Статистика\n\n")
            .append("| Метрика         | Значение |\n")
            .append("|-----------------|----------|\n")
            .append("| Количество запросов | ").append(report.requestCount()).append(END_TABLE_LINE)
            .append("| Средний размер   | ").append(report.avgSize()).append(END_TABLE_LINE)
            .append("| 95-й перцентиль | ").append(report.percentile()).append(" |\n\n");
        return str;
    }

    private StringBuilder resources(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("## Ресурсы\n\n")
            .append("| Ресурс               | Количество запросов |\n")
            .append("|----------------------|---------------------|\n");
        report.resources().forEach((resource, count) ->
            str.append("| ").append(resource).append(FIELD_BORDER).append(count).append(END_TABLE_LINE));
        return str;
    }

    private StringBuilder codes(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("\n## Коды ответов\n\n")
            .append("| Код  | Название | Количество |\n")
            .append("|------|----------|------------|\n");
        report.popularCodes().forEach((code, count) ->
            str.append("| ").append(code).append("|").append(HttpStatusCodeMapper.getDescription(code))
                .append(FIELD_BORDER).append(count).append(END_TABLE_LINE));
        return str;
    }
}
