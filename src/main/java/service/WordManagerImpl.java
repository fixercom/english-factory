package service;

import entity.Word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class WordManagerImpl implements WordManager {

    private long current_id;
    private final Map<Long, Word> dictionary;
    private final List<Long> randomIdSupplierList;

    public WordManagerImpl() {
        this.dictionary = new HashMap<>();
        randomIdSupplierList = new ArrayList<>();
        current_id = 0;
    }

    @Override
    public void addWordsToDictionary(List<Word> wordList) {
        wordList.forEach((word) -> {
            long id = ++current_id;
            word.setId(id);
            dictionary.put(id, word);
        });
        fillRandomIdSupplierList();
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
    public void saveStatisticToFile(String path) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            if (!file.exists()) {
                file.createNewFile();
            }
            Map<Integer, List<Word>> statistic = new TreeMap<>(Comparator.reverseOrder());
            for (Word word : dictionary.values()) {
                int key = word.getMaxWrongCount();
                List<Word> words = statistic.getOrDefault(key, new ArrayList<>());
                words.add(word);
                statistic.put(key, words);
            }
            for (Integer key : statistic.keySet()) {
                List<Word> words = statistic.get(key);
                for (Word word : words) {
                    writer.write(word.getMaxWrongCount()
                            + word.getValue()
                            + "=" + word.getTranslate()
                            + "=" + word.getTranscription()
                            + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillRandomIdSupplierList() {
        randomIdSupplierList.addAll(dictionary.keySet());
    }
}
