package com.learning.basics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TextAnalyzer implements ITextAnalyzer {
    private final File file;
    private final Path path;

    private static Map<String, TextAnalyzer> files = new HashMap<>();

    private TextAnalyzer(String filePath) throws FileNotFoundException {
        this.file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Invalid file path: " + filePath);
        }
        this.path = file.toPath();
    }

    /**
     * Factory method;
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    static ITextAnalyzer newInstance(String filePath) throws FileNotFoundException {
        if (!files.containsKey(filePath)) {
            files.put(filePath, new TextAnalyzer(filePath));
        }
        return files.get(filePath);
    }

    static TextAnalyzer newInstance(String filePath, String encoding) throws FileNotFoundException {
        if ("UTF-8".equals(encoding)) {
            return new TextAnalyzer(filePath);
        } else {
            return files.get(filePath);
        }
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
