package backend.academy.loganalyzer.analyzer.impl;

import backend.academy.loganalyzer.analyzer.Analyzer;
import backend.academy.loganalyzer.connection.Connection;
import backend.academy.loganalyzer.constants.Constants;
import backend.academy.loganalyzer.exceptions.MaxRetriesExceededException;
import backend.academy.loganalyzer.exceptions.UrlReadException;
import backend.academy.loganalyzer.filtration.LogDateFilter;
import backend.academy.loganalyzer.filtration.LogFiltration;
import backend.academy.loganalyzer.models.Log;
import backend.academy.loganalyzer.models.LogSummary;
import backend.academy.loganalyzer.parser.LogParser;
import backend.academy.loganalyzer.validators.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UrlAnalyzer implements Analyzer {
    private final String url;
    private final LogFiltration filtration;
    private final LogDateFilter dateFilter;
    private final HttpClient client;

    @Override
    public LogSummary analyze() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(Constants.READ_TIMEOUT))
            .GET()
            .build();

        Connection connection = new Connection(client, request);

        int numberOfProcessedLines = 0;
        LogSummary logSummary = new LogSummary();

        int attempt = 0;
        while (attempt < Constants.MAX_RETRIES) {
            attempt++;

            try (
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
            ) {
                String line;
                int currLine = 0;
                while ((line = reader.readLine()) != null) {
                    if (currLine >= numberOfProcessedLines) {
                        Log logModel = LogParser.parse(line);
                        if (Validator.isValidLog(logModel, filtration, dateFilter)) {
                            logSummary.addLog(logModel);
                        }
                        numberOfProcessedLines++;
                    }
                    currLine++;
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
}

