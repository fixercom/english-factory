package service.reader;

import entity.Word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class WordReaderImpl implements WordReader {
    @Override
    public List<Word> readWordsFromFile(String path) {
        String content;
        try {
            content = Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Arrays.stream(content.split("\n"))
                .map(this::parseWordFromLineOfFile)
                .collect(Collectors.toList());
    }

    @Override
    public List<Word> readWordsFromFiles(List<String> paths) {
        List<Word> wordsFromAllPaths = new ArrayList<>();
        paths.stream()
                .map(this::readWordsFromFile)
                .forEach(wordsFromAllPaths::addAll);
        return wordsFromAllPaths;
    }

    private Word parseWordFromLineOfFile(String lineOfFile) {
        String[] elements = lineOfFile.split("=");
        String value = elements[0];
        String translate = elements[1];
        String transcription = elements[2];
        return new Word(value, translate, transcription);
    }
}
