package backend.academy.loganalyzer.spliterator;

import backend.academy.loganalyzer.exceptions.SegmentationErrorException;
import backend.academy.loganalyzer.models.Segment;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;

@AllArgsConstructor
@Getter
public class SegmentSpliterator {
    private static final int SMALL_SEGMENT_SIZE = 256;
    private final Path file;

    private int numberOfSegments;

    public List<Segment> split() {
        List<Segment> segments = new ArrayList<>(numberOfSegments);

        try {
            var fileSize = Files.size(file);
            var segmentSize = fileSize / numberOfSegments;

            if (segmentSize <= SMALL_SEGMENT_SIZE) { // small segment: 1 is enough
                numberOfSegments = 1;
            }

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
        } catch (IOException e) {
            throw new SegmentationErrorException();
        }

        return segments;
    }

    private long normalize(ByteBuffer buff, int lastPosInBuff, long pos) {
        int size = lastPosInBuff;
        while (buff.get(size) != '\n') {
            size--;
        }

        buff.limit(size + 1);
        return pos + (size + 1);
    }
}
