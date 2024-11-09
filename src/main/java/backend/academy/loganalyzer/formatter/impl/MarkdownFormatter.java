package backend.academy.loganalyzer.formatter.impl;

import backend.academy.loganalyzer.exceptions.FileWriteException;
import backend.academy.loganalyzer.formatter.ReportFormatter;
import backend.academy.loganalyzer.httpcodes.HttpStatusCodeMapper;
import backend.academy.loganalyzer.models.Report;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MarkdownFormatter implements ReportFormatter {

    @Override
    public void format(Report report, String path) {
        StringBuilder markdown = new StringBuilder();

        // Основная информация
        markdown.append("## Основная информация\n\n")
            .append("| Параметр       | Значение |\n")
            .append("|----------------|----------|\n")
            .append("| Формат         | ").append(report.format()).append(" |\n")
            .append("| Дата с        | ").append(report.from()).append(" |\n")
            .append("| Дата по       | ").append(report.to()).append(" |\n")
            .append("| Фильтр        | ").append(report.filter()).append(" |\n")
            .append("| Значение фильтра | ").append(report.filterValue()).append(" |\n\n");

        // Статистика
        markdown.append("## Статистика\n\n")
            .append("| Метрика         | Значение |\n")
            .append("|-----------------|----------|\n")
            .append("| Количество запросов | ").append(report.requestCount()).append(" |\n")
            .append("| Средний размер   | ").append(report.avgSize()).append(" |\n")
            .append("| 95-й перцентиль | ").append(report.percentile()).append(" |\n\n");

        // Ресурсы
        markdown.append("## Ресурсы\n\n")
            .append("| Ресурс               | Количество запросов |\n")
            .append("|----------------------|---------------------|\n");
        report.resources().forEach((resource, count) ->
            markdown.append("| ").append(resource).append(" | ").append(count).append(" |\n"));

        // Коды ответов
        markdown.append("\n## Коды ответов\n\n")
            .append("| Код  | Название | Количество |\n")
            .append("|------|----------|------------|\n");
        report.popularCodes().forEach((code, count) ->
            markdown.append("| ").append(code).append("|").append(HttpStatusCodeMapper.getDescription(code))
                .append(" | ").append(count).append(" |\n"));

        // Запись в файл
        try {
            Files.writeString(Path.of(path, "report.md"), markdown.toString(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new FileWriteException();
        }
    }
}
