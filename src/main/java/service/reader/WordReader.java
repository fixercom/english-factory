package service.reader;

import entity.Word;

import java.util.List;

public interface WordReader {
    List<Word> readWordsFromFile(String path);
}
