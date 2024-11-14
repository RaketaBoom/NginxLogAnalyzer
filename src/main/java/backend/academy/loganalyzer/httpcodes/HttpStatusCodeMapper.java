package backend.academy.loganalyzer.httpcodes;

import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpStatusCodeMapper {

    private static final Map<Integer, String> CODE_DESCRIPTIONS = Map.ofEntries(
        // 1xx Informational
        Map.entry(100, "Continue"),
        Map.entry(101, "Switching Protocols"),
        Map.entry(102, "Processing"),
        Map.entry(103, "Early Hints"),
        // 2xx Success
        Map.entry(200, "OK"),
        Map.entry(201, "Created"),
        Map.entry(202, "Accepted"),
        Map.entry(203, "Non-Authoritative Information"),
        Map.entry(204, "No Content"),
        Map.entry(205, "Reset Content"),
        Map.entry(206, "Partial Content"),
        Map.entry(207, "Multi-Status"),
        Map.entry(208, "Already Reported"),
        Map.entry(226, "IM Used"),
        // 3xx Redirection
        Map.entry(300, "Multiple Choices"),
        Map.entry(301, "Moved Permanently"),
        Map.entry(302, "Found"),
        Map.entry(303, "See Other"),
        Map.entry(304, "Not Modified"),
        Map.entry(305, "Use Proxy"),
        Map.entry(306, "Unused"),
        Map.entry(307, "Temporary Redirect"),
        Map.entry(308, "Permanent Redirect"),
        // 4xx Client Error
        Map.entry(400, "Bad Request"),
        Map.entry(401, "Unauthorized"),
        Map.entry(402, "Payment Required"),
        Map.entry(403, "Forbidden"),
        Map.entry(404, "Not Found"),
        Map.entry(405, "Method Not Allowed"),
        Map.entry(406, "Not Acceptable"),
        Map.entry(407, "Proxy Authentication Required"),
        Map.entry(408, "Request Timeout"),
        Map.entry(409, "Conflict"),
        Map.entry(410, "Gone"),
        Map.entry(411, "Length Required"),
        Map.entry(412, "Precondition Failed"),
        Map.entry(413, "Payload Too Large"),
        Map.entry(414, "URI Too Long"),
        Map.entry(415, "Unsupported Media Type"),
        Map.entry(416, "Range Not Satisfiable"),
        Map.entry(417, "Expectation Failed"),
        Map.entry(418, "I'm a teapot"),
        Map.entry(419, "Authentication Timeout"),
        Map.entry(421, "Misdirected Request"),
        Map.entry(422, "Unprocessable Entity"),
        Map.entry(423, "Locked"),
        Map.entry(424, "Failed Dependency"),
        Map.entry(425, "Too Early"),
        Map.entry(426, "Upgrade Required"),
        Map.entry(428, "Precondition Required"),
        Map.entry(429, "Too Many Requests"),
        Map.entry(431, "Request Header Fields Too Large"),
        Map.entry(449, "Retry With"),
        Map.entry(451, "Unavailable For Legal Reasons"),
        Map.entry(499, "Client Closed Request"),
        // 5xx Server Error
        Map.entry(500, "Internal Server Error"),
        Map.entry(501, "Not Implemented"),
        Map.entry(502, "Bad Gateway"),
        Map.entry(503, "Service Unavailable"),
        Map.entry(504, "Gateway Timeout"),
        Map.entry(505, "HTTP Version Not Supported"),
        Map.entry(506, "Variant Also Negotiates"),
        Map.entry(507, "Insufficient Storage"),
        Map.entry(508, "Loop Detected"),
        Map.entry(509, "Bandwidth Limit Exceeded"),
        Map.entry(510, "Not Extended"),
        Map.entry(511, "Network Authentication Required"),
        Map.entry(520, "Unknown Error"),
        Map.entry(521, "Web Server Is Down"),
        Map.entry(522, "Connection Timed Out"),
        Map.entry(523, "Origin Is Unreachable"),
        Map.entry(524, "A Timeout Occurred"),
        Map.entry(525, "SSL Handshake Failed"),
        Map.entry(526, "Invalid SSL Certificate")
    );

    public static String getDescription(int code) {
        return CODE_DESCRIPTIONS.getOrDefault(code, "Unknown Status Code");
    }
}
