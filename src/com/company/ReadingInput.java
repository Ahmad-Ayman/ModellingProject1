package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class ReadingInput {
    private String filePath;
    String input;
    int numberOfJobs;
    public ReadingInput(String filePath) throws IOException {
        this.filePath = filePath;
        input = readInputFile(this.filePath);
        numberOfJobs = lineCounter(filePath);
    }

    private static String readInputFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    static int lineCounter(String path) throws IOException {

        int lineCount = 0, commentsCount = 0;

        Scanner input = new Scanner(new File(path));
        while (input.hasNextLine()) {
            String data = input.nextLine();
            lineCount++;
        }
        return lineCount;

    }
}
