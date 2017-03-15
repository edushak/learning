package com.learning.basics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TextAnalyzer {
    private final File file;
    private final Path path;

    public TextAnalyzer(String filePath) throws FileNotFoundException {
        this.file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Invalid file path: " + filePath);
        }
        this.path = file.toPath();
    }

    public Map getStats() throws IOException {
        return new HashMap<String, Integer>() {{
            put("characters", getCharactersCounter());
            put("words", getWordsCounter());
            put("lines", getLinesCounter());
        }};
    }

    public List<String> getLines() throws IOException {
        return Files.readAllLines(path);
    }
    public int getLinesCounter() throws IOException {
        return getLines().size();
    }


    public List<String> getWords() throws IOException {
        List<String> words = Arrays.asList(getText().trim().split("\\s+"));
        // filter out empty strings that is a result os an empty line
        words.removeIf(word -> word.equals(""));
        return words;
    }
    public int getWordsCounter() throws IOException {
        return getWords().size();
    }

    public char[] getCharacters() throws IOException {
        return getText().toCharArray();
    }
    public int getCharactersCounter() throws IOException {
        return getCharacters().length;
    }

    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(path);
    }
    public int getBytesCounter() throws IOException {
        return getBytes().length;
    }

    public String getText() throws IOException {
        return new String(getBytes());
    }

    @Override
    public String toString() {
        return "TextAnalyzer { path=" + path + '}';
    }
}
