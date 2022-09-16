import entity.Word;
import service.manager.WordManager;
import service.manager.WordManagerImpl;
import service.printer.Printer;
import service.printer.PrinterImpl;
import service.reader.WordReader;
import service.reader.WordReaderImpl;
import service.writer.StatisticWriter;
import service.writer.StatisticWriterImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        StatisticWriter statisticWriter = new StatisticWriterImpl();
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
            if (wordManager.isTrueAnswer(answer, word)) {
                wordManager.markWordAsRight(word);
                printer.showTranscription(word);
            } else {
                wordManager.markWordAsWrong(word);
                word.setMaxWrongCount(word.getMaxWrongCount() + 1);
                printer.showWrongMessage(word);
            }
        }
        Path pathForStatistic = createValidPath(pathForResultTest + fileName);
        wordManager.saveStatistic();
        statisticWriter.write(wordManager.getStatistic(), pathForStatistic);
    }

    private static Path createValidPath(String pathName) {
        Path path = Path.of(pathName);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
}
