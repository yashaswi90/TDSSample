package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.mockStatic;

class FileReverseTest {

    private FileReverse fileReverse;

    private Path inputFilePath;
    private Path outputFilePath;


    @BeforeEach
    void setUp() {
        fileReverse = new FileReverse();
        inputFilePath = Paths.get("input.txt");
        outputFilePath = Paths.get("output.txt");
    }

    @Test
    void reverseFileContent() throws IOException {
        String inputContent = "ABC";
        String outputContent = "CBA";
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(inputFilePath)).thenReturn(inputContent.getBytes());

            mockedFiles.when(() -> Files.write(outputFilePath, outputContent.getBytes())).thenAnswer(invocation -> null);

            fileReverse.reverseFileContent(inputFilePath, outputFilePath);

            mockedFiles.verify(() -> Files.write(outputFilePath, outputContent.getBytes()));
        }

    }

    @Test
    void whenFileThrowException() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(inputFilePath)).thenThrow(IOException.class);

            Assertions.assertThrows(RuntimeException.class, () ->
                    fileReverse.reverseFileContent(inputFilePath, outputFilePath));

        }
    }
}