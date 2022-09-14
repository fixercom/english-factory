package service;

import entity.Word;

import java.util.List;
import java.util.Map;

public interface WordManager {
    void addWordsToDictionary(List<Word> wordList);

    Word getNextWord();

    void markWordAsWrong(Word word);

    void markWordAsRight(Word word);

    boolean hasNextWord();

    int wordsLeft();

    void saveStatisticToFile(String path);
}
