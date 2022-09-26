package service.manager;

import entity.Statistic;
import entity.Word;

import java.util.*;

public class WordManagerImpl implements WordManager {

    private long current_id;
    private final Map<Long, Word> dictionary;
    private final List<Long> randomIdSupplierList;
    private final Statistic statistic;

    public WordManagerImpl() {
        this.dictionary = new HashMap<>();
        randomIdSupplierList = new ArrayList<>();
        current_id = 0;
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
    public void saveStatistic() {
        statistic.addWordsFromDictionary(dictionary.values());
    }

    @Override
    public List<String> getStatistic() {
        return statistic.getStatisticList();
    }

    @Override
    public boolean isTrueAnswer(String answer, Word word) {
        return answer.equalsIgnoreCase(word.getValue());
    }

    private void fillRandomIdSupplierList() {
        randomIdSupplierList.addAll(dictionary.keySet());
    }
}
