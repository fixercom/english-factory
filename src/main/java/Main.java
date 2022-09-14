import entity.Word;
import service.WordManager;
import service.WordManagerImpl;
import service.WordReader;
import service.WordReaderImpl;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        WordReader wordReader = new WordReaderImpl();
        List<Word> wordList = wordReader.readWordsFromFile("src/main/resources/phrasal_verbs_5");

        WordManager wordManager = new WordManagerImpl();
        wordManager.addWordsToDictionary(wordList);

        Scanner scanner = new Scanner(System.in);

        while (wordManager.hasNextWord()) {
            Word word = wordManager.getNextWord();
            System.out.print("(" + wordManager.wordsLeft() + ") ");
            System.out.print(word.getTranslate() + " ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(word.getValue())) {
                wordManager.markWordAsRight(word);
                System.out.println("    " + word.getTranscription());
            } else {
                wordManager.markWordAsWrong(word);
                word.setMaxWrongCount(word.getMaxWrongCount() + 1);
                System.out.println("            WRONG[" + word.getValue().toUpperCase()
                        + " " + word.getTranscription() + "]"
                        + " *" + word.getAttempts() + "*");
            }
        }
        wordManager.saveStatisticToFile("src/main/resources/result/phrasal_verbs_5");
    }
}
