package backend.academy.loganalyzer.models;

import java.time.OffsetDateTime;

public record Log(
    String ip,
    OffsetDateTime timestamp,
    String method,
    String resource,
    String protocol,
    Integer code,
    Integer size,
    String referer,
    String agent
) {
}
