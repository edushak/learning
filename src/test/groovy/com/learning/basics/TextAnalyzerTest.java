package com.learning.basics;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TextAnalyzerTest {
    String testFileName = "text.txt";
    String expectedFileContent =
        "" + System.lineSeparator() +
        "This is a sample" + System.lineSeparator() +
        "test file" + System.lineSeparator() +
        "of no significance." + System.lineSeparator() +
        "" + System.lineSeparator();
    TextAnalyzer analyzer;

    @BeforeClass // runs once per TextAnalyzerTest
    public void setUp() throws Exception {
        ClassLoader classLoader = TextAnalyzerTest.class.getClassLoader();
        String pathname = classLoader.getResource(testFileName).getFile();
        analyzer = new TextAnalyzer(pathname);
    }

    @Test(expectedExceptions = FileNotFoundException.class,
          expectedExceptionsMessageRegExp = "Invalid file path: C://non-existent-file.txt")
    public void testInvalidParameter() throws Exception {
        new TextAnalyzer("C://non-existent-file.txt");
    }

    @Test
    public void testGetLines() throws Exception {
        List<String> expectedLines = Arrays.asList(
            "",
            "This is a sample",
            "test file",
            "of no significance.",
            ""
        );
        assertEquals(analyzer.getLines(), expectedLines);
    }
    @Test
    public void testGetLinesCounter() throws Exception {
        assertEquals(analyzer.getLinesCounter(), 5);
    }


    @Test
    public void testGetWords() throws Exception {
        List<String> expectedWords = Arrays.asList(
            "This", "is", "a", "sample", "test", "file", "of", "no", "significance."
        );
        assertEquals(analyzer.getWords(), expectedWords);
    }
    @Test
    public void testGetWordsCounter() throws Exception {
        assertEquals(analyzer.getWordsCounter(), 9);
    }


    @Test
    public void testGetCharacters() throws Exception {
        assertEquals(analyzer.getCharacters(), expectedFileContent.toCharArray());
    }
    @Test
    public void testGetCharactersCounter() throws Exception {
        assertEquals(analyzer.getCharactersCounter(), 54);
    }

    @Test
    public void testGetBytesCounter() throws Exception {
        assertEquals(analyzer.getBytesCounter(), 54);
    }

    @Test
    public void testGetText() throws Exception {
        assertEquals(analyzer.getText(), expectedFileContent);
    }

    @Test
    public void testGetStats() throws Exception {
        Map<String, Integer> expectedStats = new HashMap<String, Integer>() {{
            put("characters", 54);
            put("words", 9);
            put("lines", 5);
        }};
        assertEquals(analyzer.getStats(), expectedStats);
    }

    @Test
    public void testToString() throws Exception {
        String actual = analyzer.toString();
        assertTrue(actual.startsWith("TextAnalyzer { path="));
        assertTrue(actual.endsWith("learning\\build\\resources\\test\\text.txt}"));
    }
}