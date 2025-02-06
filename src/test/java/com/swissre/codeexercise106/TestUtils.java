package com.swissre.codeexercise106;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestUtils {

    public static void createCsvFile(String filePath, String csvData) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(csvData);
        }
    }
}
