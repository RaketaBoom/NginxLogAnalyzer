package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.exceptions.ConnectionException;
import backend.academy.loganalyzer.exceptions.MaxRetriesExceededException;
import backend.academy.loganalyzer.exceptions.UrlReadException;
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

@Slf4j
@RequiredArgsConstructor
public class UrlAnalyzer implements Analyzer {
    private final String url;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;
    private final HttpClient client;

    @Override
    public LogSummary analyze() {
        int currLine = 0;
        LogSummary logSummary = new LogSummary();

        int attempt = 0;

        while (attempt < Constants.MAX_RETRIES) {
            attempt++;
            try (InputStream inputStream = connect();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                int lineCount = 0;

                // Чтение данных с пропуском уже обработанных строк
                while ((line = reader.readLine()) != null) {
                    if (lineCount >= currLine) {
                        Log logModel = LogParser.parse(line);
                        if (isValidLog(logModel)) {
                            logSummary.addLog(logModel);
                        }
                        currLine++;
                    }
                    lineCount++;
                }

                return logSummary;

            } catch (IOException e) {
                throw new UrlReadException();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new UrlReadException();
            }
        }

        throw new MaxRetriesExceededException(url);
    }

    private boolean isValidLog(Log logModel) {
        return filtration.matches(logModel) && dateFilter.isDateInRange(logModel);
    }

    private InputStream connect() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(Constants.READ_TIMEOUT))
            .GET()
            .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 200) {
            return response.body();
        }
        throw new ConnectionException(response.statusCode());
    }
}

