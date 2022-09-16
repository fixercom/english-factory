import entity.Word;
import service.manager.WordManager;
import service.manager.WordManagerImpl;
import service.printer.Printer;
import service.printer.PrinterImpl;
import service.reader.WordReader;
import service.reader.WordReaderImpl;

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
        WordManager wordManager = new WordManagerImpl();
        Printer printer = new PrinterImpl(wordManager);
        Scanner scanner = new Scanner(System.in);

        List<Word> words = wordReader.readWordsFromFile(path + fileName);
        wordManager.addWordsToDictionary(words);

        while (wordManager.hasNextWord()) {
            Word word = wordManager.getNextWord();
            printer.showQuantityOfWordsLeft();
            printer.showRussianWord(word);
            String answer = scanner.nextLine();
            if (wordManager.isTrueAnswer(answer,word)) {
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
