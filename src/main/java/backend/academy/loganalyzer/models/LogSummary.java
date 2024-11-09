package backend.academy.loganalyzer.models;

import backend.academy.loganalyzer.exceptions.EmptyLogException;
import com.tdunning.math.stats.TDigest;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import static backend.academy.loganalyzer.constants.Constants.COMPRESSION_FACTOR;

@Getter
public class LogSummary {
    private int count;
    private double sum;
    private TDigest tDigest;
    private Map<String, Integer> popularResources;
    private Map<Integer, Integer> popularCodes;

    public LogSummary() {
        count = 0;
        sum = 0d;
        tDigest = TDigest.createDigest(COMPRESSION_FACTOR);
        popularResources = new HashMap<>();
        popularCodes = new HashMap<>();
    }

    public LogSummary addLog(Log logModel) {
        if (logModel == null) {
            throw new EmptyLogException();
        }
        count++;
        sum += logModel.size();
        tDigest.add(logModel.size());

        popularResources.compute(logModel.resource(), (key, value) -> value == null ? 1 : value + 1);
        popularCodes.compute(logModel.code(), (key, value) -> value == null ? 1 : value + 1);

        return this;
    }
}
