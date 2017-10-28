package com.learning.basics;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ITextAnalyzer {
    Map getStats() throws IOException;

    List<String> getLines() throws IOException;
    int getLinesCounter() throws IOException;

    List<String> getWords() throws IOException;
    int getWordsCounter() throws IOException;

    char[] getCharacters() throws IOException;
    int getCharactersCounter() throws IOException;

    byte[] getBytes() throws IOException;
    int getBytesCounter() throws IOException;

    String getText() throws IOException;
}
