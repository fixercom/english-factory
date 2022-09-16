package service.printer;

import entity.Word;
import service.manager.WordManager;

public class PrinterImpl implements Printer {
    private final WordManager wordManager;

    public PrinterImpl(WordManager wordManager) {
        this.wordManager = wordManager;
    }

    @Override
    public void showQuantityOfWordsLeft() {
        System.out.print("(" + wordManager.wordsLeft() + ") ");
    }

    @Override
    public void showRussianWord(Word word) {
        System.out.print(word.getTranslate() + " ");
    }

    @Override
    public void showTranscription(Word word) {
        System.out.println("    " + word.getTranscription());
    }

    @Override
    public void showWrongMessage(Word word) {
        System.out.println("            WRONG[" + word.getValue().toUpperCase()
                + " " + word.getTranscription() + "]"
                + " *" + word.getAttempts() + "*");
    }
}
