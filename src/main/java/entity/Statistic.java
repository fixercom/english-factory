package entity;

import java.util.*;
import java.util.stream.Collectors;

public class Statistic {
    private final Map<Integer, List<Word>> statistic;

    public Statistic() {
        statistic = new TreeMap<>(Comparator.reverseOrder());
    }

    public void addWordsFromDictionary(Collection<Word> wordCollection) {
        wordCollection.forEach(this::addWordToStatisticMap);
    }

    private void addWordToStatisticMap(Word word) {
        int quantityOfWrongAnswer = word.getMaxWrongCount();
        List<Word> words = getListForCurrentWord(quantityOfWrongAnswer);
        words.add(word);
        statistic.put(quantityOfWrongAnswer, words);
    }

    private List<Word> getListForCurrentWord(int quantityOfWrongAnswer) {
        return statistic.getOrDefault(quantityOfWrongAnswer, new ArrayList<>());
    }

    public List<String> getStatisticList() {
        List<Word> words = new ArrayList<>();
        statistic.values().forEach(words::addAll);
        return words.stream()
                .map(this::getStatisticLineFromWord)
                .collect(Collectors.toList());
    }

    private String getStatisticLineFromWord(Word word) {
        return word.getMaxWrongCount()
                + "=" + word.getValue()
                + "=" + word.getTranslate()
                + "=" + word.getTranscription()
                + "\n";
    }
}
