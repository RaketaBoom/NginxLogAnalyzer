package backend.academy;

import backend.academy.loganalyzer.LogAnalyzerApi;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        LogAnalyzerApi logAnalyzerApi = new LogAnalyzerApi(args);
        logAnalyzerApi.start();
    }
}
