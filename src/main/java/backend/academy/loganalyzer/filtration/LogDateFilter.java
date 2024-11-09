package backend.academy.loganalyzer.filtration;

import backend.academy.loganalyzer.models.Log;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogDateFilter {
    private final LocalDate from;
    private final LocalDate to;

    public boolean check(Log log) {
        LocalDate logDate = LocalDate.from(log.timestamp());

        if (from != null && logDate.isBefore(from)) {
            return false;
        }

        return to == null || !logDate.isAfter(to);
    }
}
