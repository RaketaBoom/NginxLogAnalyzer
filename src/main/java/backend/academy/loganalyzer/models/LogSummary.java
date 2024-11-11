package backend.academy.loganalyzer.models;

import backend.academy.loganalyzer.exceptions.EmptyLogException;
import com.tdunning.math.stats.TDigest;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import static backend.academy.loganalyzer.constants.Constants.COMPRESSION_FACTOR;

@Getter
@AllArgsConstructor
public class LogSummary {
    private int count;
    private double sum;
    private TDigest tDigest;
    private Map<String, Integer> resources;
    private Map<Integer, Integer> codes;

    public LogSummary() {
        count = 0;
        sum = 0d;
        tDigest = TDigest.createDigest(COMPRESSION_FACTOR);
        resources = new HashMap<>();
        codes = new HashMap<>();
    }

    public LogSummary addLog(Log logModel) {
        if (logModel == null) {
            throw new EmptyLogException();
        }
        count++;
        sum += logModel.size();
        tDigest.add(logModel.size());

        resources.compute(logModel.resource(), (key, value) -> value == null ? 1 : value + 1);
        codes.compute(logModel.code(), (key, value) -> value == null ? 1 : value + 1);

        return this;
    }

    public LogSummary add(LogSummary other) {
        this.count += other.count;
        this.sum += other.sum;
        this.tDigest.add(other.tDigest);

        other.resources.forEach((key, value) ->
            this.resources.merge(key, value, Integer::sum)
        );

        other.codes.forEach((key, value) ->
            this.codes.merge(key, value, Integer::sum)
        );

        return this;
    }
}
