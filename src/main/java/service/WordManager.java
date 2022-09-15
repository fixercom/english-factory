package service;

import entity.Word;

import java.util.List;

public interface WordManager {
    void addWordsToDictionary(List<Word> wordList);

    Word getNextWord();

    void markWordAsWrong(Word word);

    void markWordAsRight(Word word);

    boolean hasNextWord();

    int wordsLeft();

    void saveStatistic(String path);
}
