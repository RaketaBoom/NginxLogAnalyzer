package backend.academy.loganalyzer.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Filter {
    IP("ip"),
    METHOD("method"),
    RESOURCE("resource"),
    PROTOCOL("protocol"),
    CODE("code"),
    SIZE("size"),
    REFERER("referer"),
    AGENT("agent");

    private final String label;

    private static final Map<String, Filter> BY_LABEL = new HashMap<>();

    static {
        for (var value : values()) {
            BY_LABEL.put(value.label(), value);
        }
    }

    public static Filter valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
