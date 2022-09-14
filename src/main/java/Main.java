import entity.Word;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WordReader wordReader = new WordReaderImpl();
        List<Word> wordList = wordReader.readWordsFromFile("src/main/resources/phrasal_verbs_5");

        WordManager wordManager = new WordManagerImpl();
        wordManager.addWordsToDictionary(wordList);

        Printer printer = new PrinterImpl(wordManager);

        Scanner scanner = new Scanner(System.in);

        while (wordManager.hasNextWord()) {
            Word word = wordManager.getNextWord();
            printer.showQuantityOfWordsLeft();
            printer.showRussianWord(word);
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(word.getValue())) {
                wordManager.markWordAsRight(word);
                printer.showTranscription(word);
            } else {
                wordManager.markWordAsWrong(word);
                word.setMaxWrongCount(word.getMaxWrongCount() + 1);
                printer.showWrongMessage(word);
            }
        }
        wordManager.saveStatisticToFile("src/main/resources/result/phrasal_verbs_5");
    }
}
