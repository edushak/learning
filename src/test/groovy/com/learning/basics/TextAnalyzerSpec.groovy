package com.learning.basics

import spock.lang.Shared
import spock.lang.Specification

class TextAnalyzerSpec extends Specification {

    @Shared String testFileName = "text.txt";
    @Shared String expectedFileContent =
            "" + System.lineSeparator() +
            "This is a sample" + System.lineSeparator() +
            "test file" + System.lineSeparator() +
            "of no significance." + System.lineSeparator() +
            "" + System.lineSeparator();
    @Shared ITextAnalyzer analyzer;

    // runs once per TextAnalyzerTest
    def setup() {
        ClassLoader classLoader = TextAnalyzerTest.class.getClassLoader();
        URL resource = classLoader.getResource(testFileName);
        String pathname = resource.getFile();
        analyzer = TextAnalyzer.newInstance(pathname);
    }

    def testInvalidParameter() {
        when:
        TextAnalyzer.newInstance("C://non-existent-file.txt");
        then:
        FileNotFoundException ex = thrown()
        ex.message == "Invalid file path: C://non-existent-file.txt"
    }

    def testGetLines() throws Exception {
        given:
        List<String> expectedLines = [
            "",
            "This is a sample",
            "test file",
            "of no significance.",
            ""
        ]
        when:
        def lines = analyzer.getLines()
        then:
        lines == expectedLines
    }

    void testGetLinesCounter() throws Exception {
        when:
        def counter = analyzer.getLinesCounter()
        then:
        counter == 5
    }

    void testGetWords() throws Exception {
        given:
        List<String> expectedWords = ["This", "is", "a", "sample", "test", "file", "of", "no", "significance."]
        when:
        def words = analyzer.getWords()
        then:
        words == expectedWords
    }

    void testGetWordsCounter() throws Exception {
        expect:
        analyzer.getWordsCounter() == 9
    }

    void testGetCharacters() throws Exception {
        expect:
        analyzer.getCharacters() == expectedFileContent.toCharArray()
    }

    void testGetCharactersCounter() throws Exception {
        expect:
        analyzer.getCharactersCounter() == 54
    }

    void testGetBytesCounter() throws Exception {
        expect:
        analyzer.getBytesCounter() == 54
    }

    void testGetText() throws Exception {
        expect:
        analyzer.getText() == expectedFileContent
    }

    void testGetStats() throws Exception {
        given:
        Map<String, Integer> expectedStats = new HashMap<String, Integer>() {{
            put("characters", 54);
            put("words", 9);
            put("lines", 5);
        }};
        expect:
        analyzer.getStats() == expectedStats
    }

    void testToString() {
        when:
        String actual = analyzer.toString();
        then:
        actual.startsWith("TextAnalyzer { path=")
        actual.endsWith("learning\\build\\resources\\test\\text.txt}")
    }
}
