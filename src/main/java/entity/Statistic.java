package entity;

import java.util.*;

public class Statistic {
    private final Map<Integer, List<Word>> statistic;

    public Statistic() {
        statistic = new TreeMap<>(Comparator.reverseOrder());
    }

    public void addWordsFromDictionary(Collection<Word> wordCollection) {
        wordCollection.forEach(this::addWordToStatisticMap);
    }

    private void addWordToStatisticMap(Word word){
        int quantityOfWrongAnswer = word.getMaxWrongCount();
        List<Word> words = getListForCurrentWord(quantityOfWrongAnswer);
        words.add(word);
        statistic.put(quantityOfWrongAnswer, words);
    }
    private List<Word> getListForCurrentWord(int quantityOfWrongAnswer){
        return statistic.getOrDefault(quantityOfWrongAnswer,new ArrayList<>());
    }

    public Map<Integer, List<Word>> getStatistic() {
        return statistic;
    }
}
