package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.exceptions.GlobHandleException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PathsParser {
    public static List<Path> parse(String glob) {
        List<Path> paths = new ArrayList<>();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);

        try (Stream<Path> stream = Files.walk(Paths.get(""))) {
            stream.filter(path -> matcher.matches(path) && path.toString().endsWith(".txt"))
                .forEach(paths::add);
        } catch (IOException e) {
            throw new GlobHandleException();
        }

        return paths;
    }
}
