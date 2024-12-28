package backend.academy.loganalyzer.writer.impl;

import backend.academy.loganalyzer.exceptions.FileWriteException;
import backend.academy.loganalyzer.httpcodes.HttpStatusCodeMapper;
import backend.academy.loganalyzer.models.Report;
import backend.academy.loganalyzer.writer.ReportFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReportAdocWriter implements ReportFileWriter {
    private static final String CELL_BORDER = "|===\n";
    private static final String TABLE_BORDER = "|===\n\n";
    private static final String FIELD_BORDER = " | ";

    @Override
    public void createFile(Report report, String path) {
        StringBuilder adoc = new StringBuilder();

        adoc.append(files(report));
        adoc.append(parameters(report));
        adoc.append(statistic(report));
        adoc.append(resources(report));
        adoc.append(codes(report));

        try {
            Files.writeString(
                Path.of(path, "report.adoc"),
                adoc.toString(),
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            throw new FileWriteException();
        }
    }

    private StringBuilder files(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("== Файлы\n\n")
            .append(CELL_BORDER)
            .append("| Название | Ссылка\n");

        if (report.files() == null) {
            str.append(TABLE_BORDER);
            return str;
        }
        report.files()
            .forEach(x -> str
                .append("| ").append(x.getFileName()).append(FIELD_BORDER)
                .append(" link:").append(x.toString().replace('\\', '/'))
                .append("[").append(x).append("]")
                .append("\n"));
        str.append(TABLE_BORDER);
        return str;
    }

    private StringBuilder parameters(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("== Параметры\n\n")
            .append(CELL_BORDER)
            .append("| Параметр | Значение\n")
            .append("| Формат | ").append(report.format()).append("\n")
            .append(report.from() != null ? "| Начальная дата | " + report.from() + "\n" : "")
            .append(report.to() != null ? "| Конечная дата | " + report.to() + "\n" : "")
            .append(report.filter() != null ? "| Фильтр | " + report.filter() + "\n" : "")
            .append(report.filterValue() != null ? "| Значение фильтра | " + report.filterValue() + "\n" : "")
            .append(TABLE_BORDER);
        return str;
    }

    private StringBuilder statistic(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("== Статистика\n\n")
            .append(CELL_BORDER)
            .append("| Метрика | Значение\n")
            .append("| Количество запросов | ").append(report.requestCount()).append("\n")
            .append("| Средний размер | ").append(report.avgSize()).append("\n")
            .append("| 95-й перцентиль | ").append(report.percentile()).append("\n")
            .append(TABLE_BORDER);
        return str;
    }

    private StringBuilder resources(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("== Ресурсы\n\n")
            .append(CELL_BORDER)
            .append("| Ресурс | Количество запросов\n");
        report.resources().forEach((resource, count) ->
            str.append("| ").append(resource).append(FIELD_BORDER).append(count).append("\n"));
        str.append(TABLE_BORDER);
        return str;
    }

    private StringBuilder codes(Report report) {
        StringBuilder str = new StringBuilder();
        str.append("== Коды ответов\n\n")
            .append(CELL_BORDER)
            .append("| Код | Название | Количество\n");
        report.popularCodes().forEach((code, count) ->
            str.append("| ").append(code).append(FIELD_BORDER).append(HttpStatusCodeMapper.getDescription(code))
                .append(FIELD_BORDER).append(count).append("\n"));
        str.append(TABLE_BORDER);
        return str;
    }
}
