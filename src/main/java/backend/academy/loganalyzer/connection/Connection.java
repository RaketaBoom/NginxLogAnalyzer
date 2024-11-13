package backend.academy.loganalyzer.connection;

import backend.academy.loganalyzer.exceptions.ConnectionException;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Connection {
    private final HttpClient client;
    private final HttpRequest request;

    public InputStream getInputStream() throws IOException, InterruptedException {
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 200) {
            return response.body();
        }
        throw new ConnectionException(response.statusCode());
    }
}
