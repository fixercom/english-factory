package service;

import entity.Word;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface StatisticWriter {
    void writeStatisticToFile(Map<Integer, List<Word>> statistic, Path path);
}
