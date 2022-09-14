package service;

import entity.Word;

public interface Printer {
    void showQuantityOfWordsLeft();
    void showRussianWord(Word word);
    void showTranscription(Word word);
    void showWrongMessage(Word word);
}
