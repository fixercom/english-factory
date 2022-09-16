package service.writer;

import java.nio.file.Path;
import java.util.List;

public interface StatisticWriter {
    void write(List<String> statistic, Path path);
}
