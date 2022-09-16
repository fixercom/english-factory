package service.manager;

import entity.Word;

import java.util.List;
import java.util.stream.Stream;

public interface WordManager {
    void addWordsToDictionary(List<Word> wordList);

    Word getNextWord();

    void markWordAsWrong(Word word);

    void markWordAsRight(Word word);

    boolean hasNextWord();

    int wordsLeft();

    void saveStatistic();

    List<String> getStatistic();

    boolean isTrueAnswer(String answer, Word word);
}
