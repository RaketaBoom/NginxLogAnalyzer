package backend.academy.loganalyzer.spliterator;

import backend.academy.loganalyzer.models.Segment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;

@AllArgsConstructor
@Getter
public class SegmentSpliterator {
    private final Path file;

    private int numberOfSegments;

    public List<Segment> split() throws IOException {
        var fileSize = Files.size(file);
        var segmentSize = fileSize / numberOfSegments;

        if (segmentSize <= (1 << 8)) { // small segment: 1 is enough
            numberOfSegments = 1;
        }

        List<Segment> segments = new ArrayList<>(numberOfSegments);

        var pos = 0L;
        for (var s = 0; s < numberOfSegments - 1; s++) {
            try (var channel = (FileChannel) Files.newByteChannel(file, READ)) {
                var buff = channel.map(READ_ONLY, pos, segmentSize);
                pos = normalize(buff, (int) segmentSize - 1, pos);
                segments.add(new Segment(buff));
            }
        }

        // handle last segment
        try (var channel = (FileChannel) Files.newByteChannel(file, READ)) {
            var buff = channel.map(READ_ONLY, pos, fileSize - pos);
            segments.add(new Segment(buff));
        }
        return segments;
    }

    private long normalize(ByteBuffer buff, int relativePos, long pos) {
        while (buff.get(relativePos) != '\n') {
            relativePos--;
        }

        buff.limit(relativePos + 1);
        return pos + (relativePos + 1);
    }
}
