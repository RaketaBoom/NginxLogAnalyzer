package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.exceptions.DirectoryNotExistException;
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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PathsParser {
    private final String directory;

    public List<Path> parse(String glob) {
        Path dirPath = Paths.get(directory);

        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            throw new DirectoryNotExistException(directory);
        }

        List<Path> paths = new ArrayList<>();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + directory + "/" + glob);

        try (Stream<Path> stream = Files.walk(dirPath)) {
            stream.filter(path -> matcher.matches(path) && path.toString().endsWith(".txt"))
                .forEach(paths::add);
        } catch (IOException e) {
            throw new GlobHandleException();
        }

        return paths;
    }
}
