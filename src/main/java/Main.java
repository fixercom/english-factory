import entity.Word;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String parentPath = "src/main/resources/";
        String level = "elementary/";
        String chapter = "chapter4/";
        String fileName = "D";
        String path = parentPath + "dictionaries/" + level + chapter;
        String pathForResultTest = parentPath + "result/" + level + chapter;

        WordReader wordReader = new WordReaderImpl();
        List<Word> wordList = wordReader.readWordsFromFile(path + fileName);

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
        wordManager.saveStatistic(pathForResultTest + fileName);
    }
}
