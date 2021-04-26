package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CsvParser {

    private static final Logger logger = LogManager.getLogger(CsvParser.class);

    @org.jetbrains.annotations.NotNull
    public static List<String[]> readCSV(String file) {

        InputStreamReader csvFile = new InputStreamReader(Objects.requireNonNull(CsvParser.class.getClassLoader().getResourceAsStream(file)));
        String fieldDelimiter = ",";

        List<String[]> result = new ArrayList<String[]>();

        BufferedReader br;

        try {
            br = new BufferedReader(csvFile);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(fieldDelimiter, -1);
                result.add(fields);
            }

        } catch (IOException e) {
            logger.error(e);
        }

        return result;

    }

}
