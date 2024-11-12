package backend.academy.loganalyzer.utils;

import backend.academy.loganalyzer.models.LogSummary;
import lombok.experimental.UtilityClass;
import java.util.List;

@UtilityClass
public class LogSummaryMerger {
    public static LogSummary merge(List<LogSummary> results){
        LogSummary mergedResult = new LogSummary();

        results.forEach(mergedResult::add);

        return mergedResult;
    }
}
