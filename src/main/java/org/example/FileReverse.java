package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReverse {
    public void reverseFileContent(Path inputFile, Path outputFile) {
        String content;
        try {
            content = new String(Files.readAllBytes(inputFile));


            String reversedContent = new StringBuilder(content).reverse().toString();

            Files.write(outputFile, reversedContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
