package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        String inputFile = args[0];
        int targetMonth = Integer.parseInt(args[1]);
        String outputFile = args[2];

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            List<String> filteredSortedNames = lines.stream()
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length == 3)
                    .filter(parts -> {
                        LocalDate dateOfBirth = LocalDate.parse(parts[2].trim(), formatter);
                        return dateOfBirth.getMonthValue() == targetMonth;
                    })
                    .map(parts -> parts[0].trim() + " " + parts[1].trim())
                    .sorted()
                    .collect(Collectors.toList());

            Files.write(Paths.get(outputFile), filteredSortedNames);
        } catch (IOException e) {
            throw new RuntimeException("Error processing files", e);
        }
    }
}
