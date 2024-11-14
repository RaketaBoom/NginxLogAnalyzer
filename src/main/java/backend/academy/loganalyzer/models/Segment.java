package backend.academy.loganalyzer.models;

import java.nio.ByteBuffer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Segment {
    private static final int MAX_LINE_SIZE = 1024;
    private final ByteBuffer buffer;
    private int iterator = 0;

    public String readLine() {
        byte b;
        if (iterator == buffer.limit()) {
            return null;
        }
        byte[] line = new byte[MAX_LINE_SIZE];
        int offset = 0;
        while (iterator != buffer.limit() && (b = buffer.get(iterator++)) != '\n') {
            line[offset++] = b;
        }
        return new String(line, 0, offset);
    }
}
