package backend.academy.loganalyzer.formatter.impl;

import backend.academy.loganalyzer.exceptions.FileWriteException;
import backend.academy.loganalyzer.formatter.ReportFormatter;
import backend.academy.loganalyzer.models.Report;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AdocFormatter implements ReportFormatter {
    @Override
    public void format(Report report, String path) {
        StringBuilder builder = new StringBuilder();

        builder.append("= Отчет\n\n");

        // Добавляем базовую информацию
        builder.append("== Основная информация\n\n");
        builder.append("* Файлы: ").append(String.join(", ", report.files())).append("\n");
        builder.append("* Формат: ").append(report.format()).append("\n");
        builder.append("* Начальная дата: ").append(report.from()).append("\n");
        builder.append("* Конечная дата: ").append(report.to()).append("\n");
        builder.append("* Фильтр: ").append(report.filter()).append("\n");
        builder.append("* Значение фильтра: ").append(report.filterValue()).append("\n");

        // Статистика
        builder.append("\n== Статистика\n\n");
        builder.append("* Количество запросов: ").append(report.requestCount()).append("\n");
        builder.append("* Средний размер: ").append(report.avgSize()).append("\n");
        builder.append("* 95-й перцентиль: ").append(report.percentile()).append("\n");

        // Популярные ресурсы
        builder.append("\n== Ресурсы\n\n");
        report.resources().forEach((resource, count) ->
            builder.append("* ").append(resource).append(": ").append(count).append(" раз\n")
        );

        // Популярные коды
        builder.append("\n== Статистика по кодам ответов\n\n");
        report.popularCodes().forEach((code, count) ->
            builder.append("* Код ").append(code).append(": ").append(count).append(" раз\n")
        );

        // Запись в файл
        try {
            Files.write(Paths.get(path, "report.adoc"), builder.toString().getBytes());
        } catch (IOException e) {
            throw new FileWriteException();
        }
    }
}
