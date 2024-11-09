package backend.academy.loganalyzer.analyzer;

import backend.academy.loganalyzer.exceptions.ConnectionException;
import backend.academy.loganalyzer.exceptions.MaxRetriesExceededException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.Log;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.parser.LogParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static backend.academy.loganalyzer.constants.Constants.CONNECT_TIMEOUT;
import static backend.academy.loganalyzer.constants.Constants.MAX_RETRIES;
import static backend.academy.loganalyzer.constants.Constants.READ_TIMEOUT;

@Slf4j
@RequiredArgsConstructor
public class UrlAnalyzer {
    private final String url;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;

    private final HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT))
        .build();

    public LogSummary analyze() {
        int currLine = 0;
        LogSummary logSummary = new LogSummary();

        for (int i = 0; i < MAX_RETRIES; ++i) {
            try (InputStream inputStream = retry();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                int lineCount = 0;

                // Чтение данных с пропуском уже обработанных строк
                while ((line = reader.readLine()) != null) {
                    if (lineCount >= currLine) {  // Пропускаем уже обработанные строки
                        Log logModel = LogParser.parse(line);
                        if (checkLog(logModel)) {
                            logSummary.addLog(logModel);
                        }
                        currLine++;
                    }
                    lineCount++;
                }

                return logSummary;

            } catch (IOException e) {
                log.error("Ошибка ввода-вывода: {}", e.getMessage(), e);
            } catch (InterruptedException e) {
                log.error("Поток прерван");
                Thread.currentThread().interrupt();
            }
        }

        throw new MaxRetriesExceededException(url);
    }

    private boolean checkLog(Log logModel) {
        return filtration.matches(logModel) && dateFilter.check(logModel);
    }

    private InputStream retry() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(READ_TIMEOUT))
            .GET()
            .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new ConnectionException(response.statusCode());
        }
    }
}

