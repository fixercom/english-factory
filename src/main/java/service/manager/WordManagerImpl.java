package service.manager;

import entity.Statistic;
import entity.Word;
import service.writer.StatisticWriter;
import service.writer.StatisticWriterImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordManagerImpl implements WordManager {

    private long current_id;
    private final Map<Long, Word> dictionary;
    private final List<Long> randomIdSupplierList;
    private final Statistic statistic;
    private final StatisticWriter statisticWriter;

    public WordManagerImpl() {
        this.dictionary = new HashMap<>();
        randomIdSupplierList = new ArrayList<>();
        current_id = 0;
        statisticWriter = new StatisticWriterImpl();
        statistic = new Statistic();
    }

    @Override
    public void addWordsToDictionary(List<Word> wordList) {
        wordList.forEach(this::addOneWordToDictionary);
        fillRandomIdSupplierList();
    }

    private void addOneWordToDictionary(Word word) {
        long id = ++current_id;
        word.setId(id);
        dictionary.put(id, word);
    }

    @Override
    public Word getNextWord() {
        long id = randomIdSupplierList.get(new Random().nextInt(randomIdSupplierList.size()));
        return dictionary.get(id);
    }

    @Override
    public void markWordAsWrong(Word word) {
        int countOfAttempt = word.getAttempts();
        word.setAttempts(countOfAttempt + 1);
    }

    @Override
    public void markWordAsRight(Word word) {
        int countOfAttempt = word.getAttempts();
        word.setAttempts(countOfAttempt - 1);
        if (word.getAttempts() == -1) {
            randomIdSupplierList.remove(word.getId());
        }
    }

    @Override
    public boolean hasNextWord() {
        return !(randomIdSupplierList.isEmpty());
    }

    @Override
    public int wordsLeft() {
        return randomIdSupplierList.size();
    }

    @Override
    public void saveStatistic(String pathName) {
        statistic.addWordsFromDictionary(dictionary.values());
        Path path = createValidPath(pathName);
        statisticWriter.writeStatisticToFile(statistic.getStatistic(), path);
    }

    @Override
    public boolean isTrueAnswer(String answer, Word word) {
        return answer.equals(word.getValue());
    }

    private Path createValidPath(String pathName) {
        Path path = Path.of(pathName);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    private void fillRandomIdSupplierList() {
        randomIdSupplierList.addAll(dictionary.keySet());
    }
}
