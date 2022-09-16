package service.writer;

import entity.Word;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class StatisticWriterImpl implements StatisticWriter {
    @Override
    public void writeStatisticToFile(Map<Integer, List<Word>> statistic, Path path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            for (Integer key : statistic.keySet()) {
                List<Word> words = statistic.get(key);
                for (Word word : words) {
                    writer.write(word.getMaxWrongCount()
                            + "=" + word.getValue()
                            + "=" + word.getTranslate()
                            + "=" + word.getTranscription()
                            + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
