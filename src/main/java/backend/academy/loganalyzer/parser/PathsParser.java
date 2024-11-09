package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.exceptions.PathsParserException;
import lombok.experimental.UtilityClass;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PathsParser {
    public static List<Path> parse(String pathTemplate) {
        List<Path> matchedPaths = new ArrayList<>();

        // Извлекаем корневую директорию из шаблона
        Path rootPath = Paths.get(pathTemplate).getRoot();
        if (rootPath == null) {
            rootPath = Paths.get(".");
        }

        // Создаем PathMatcher для шаблона
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + pathTemplate);

        // Рекурсивно обходим файловую систему, начиная с rootPath
        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    // Если файл соответствует шаблону, добавляем его в список
                    if (pathMatcher.matches(path)) {
                        matchedPaths.add(path);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    // Продолжаем обход, даже если есть проблемы с некоторыми файлами
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new PathsParserException();
        }

        return matchedPaths;
    }
}
