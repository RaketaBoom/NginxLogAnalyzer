package backend.academy.loganalyzer.constants;

import backend.academy.loganalyzer.enums.Format;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final boolean MULTITHREADING_MODE = false;
    public static final int CONNECT_TIMEOUT = 5;
    public static final int READ_TIMEOUT = 5;
    public static final int MAX_RETRIES = 3;
    public static final double COMPRESSION_FACTOR = 100.0;
    public static final double PERCENTILE_VALUE = 0.95;
    public static final String REPORT_DIRECTORY = "";
    public static final Format DEFAULT_FORMAT = Format.MARKDOWN;

}
