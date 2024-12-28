package backend.academy.loganalyzer.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Format {
    ADOC("adoc"),
    MARKDOWN("markdown");

    private final String label;

    private static final Map<String, Format> BY_LABEL = new HashMap<>();

    static {
        for (var value : values()) {
            BY_LABEL.put(value.label(), value);
        }
    }

    public static Format valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
