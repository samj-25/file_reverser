package com.hsbc.test;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;

class FileReverserTest {

    @Test
    void reverseFileContents() {
        FileReverser fileReverser = new FileReverser();
        assertEquals("CBA", fileReverser.reverseFileContents("ABC"));
        assertEquals("", fileReverser.reverseFileContents(""));
        assertEquals("A", fileReverser.reverseFileContents("A"));
    }

    @Test
    void reverseFile_multiLinesTest() throws IOException {
        // test file
        String inputFilePath = "testInput.txt";
        String outputFilePath = "testOutput.txt";
        //String inputContents = "Hello\nWorld";
        String inputContents = "Line 1\nLine 2\nLine 3";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath))) {
            writer.write(inputContents);
        }

        // Perform the reverse operation
        FileReverser fileReverser = new FileReverser();
        fileReverser.reverseFile(inputFilePath, outputFilePath);

        // Read the contents of the output file
        StringBuilder outputContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputContents.append(line).append("\n");
            }
        }

        // Clean up
        deleteIfExists(get(inputFilePath));
        deleteIfExists(get(outputFilePath));

        // Assert the result
        //assertEquals("olleH\ndlroW\n", outputContents.toString());
        assertEquals("1 eniL\n2 eniL\n3 eniL\n", outputContents.toString());
    }

    @Test
    void reverseFile_emptyFile() throws IOException {
        // Test with an empty input file
        String emptyInputFilePath = "emptyInput.txt";
        String emptyOutputFilePath = "emptyOutput.txt";
        String emptyLineContents = "";
        write(get(emptyInputFilePath), emptyLineContents.getBytes());
        FileReverser fileReverserEmpty = new FileReverser();
        fileReverserEmpty.reverseFile(emptyInputFilePath, emptyOutputFilePath);
        assertArrayEquals("".getBytes(), readAllBytes(get(emptyOutputFilePath)));
        deleteIfExists(get(emptyInputFilePath));
        deleteIfExists(get(emptyOutputFilePath));
    }

    @Test
    void reverseFile_SingleLineFile() throws IOException {
        // Test with a file containing a single line
        String singleLineInputFilePath = "singleLineInput.txt";
        String singleLineOutputFilePath = "singleLineOutput.txt";
        String singleLineContents = "SingleLine";
        write(get(singleLineInputFilePath), singleLineContents.getBytes());
        FileReverser fileReverserSingleLine = new FileReverser();
        fileReverserSingleLine.reverseFile(singleLineInputFilePath, singleLineOutputFilePath);
        assertEquals("eniLelgniS", readAllLines(get(singleLineOutputFilePath)).get(0));
        deleteIfExists(get(singleLineInputFilePath));
        deleteIfExists(get(singleLineOutputFilePath));
    }
}
