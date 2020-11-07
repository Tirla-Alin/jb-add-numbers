package com.royalbadger.jb;

import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.quote;

public class StringAccumulator {

    private static final String DELIMITER_MARKER = "//";
    private static final int MAX_NUMBER = 1000;
    private static final String DEFAULT_DELIMITER = "[,\n]";
    private static final String DELIMITERS_DELIMITER = "\\|";

    public int add(String numbers) {
        if (numbers == null || numbers.length() == 0) {
            return 0;
        }
        String[] splitNumbers = splitNumbers(numbers);

        int result = 0;
        List<Integer> illegalArguments = new ArrayList<>();
        for (String number : splitNumbers) {
            int parsedNumber = Integer.parseInt(number);
            if (parsedNumber < 0) {
                illegalArguments.add(parsedNumber);
            } else if (parsedNumber <= MAX_NUMBER) {
                result += parsedNumber;
            }
        }

        if (illegalArguments.size() > 0) {
            throw new IllegalArgumentException("negatives not allowed " + illegalArguments);
        }
        return result;
    }

    private String[] splitNumbers(String numbers) {
        String actualNumbers;
        String delimiter;
        if (numbers.startsWith(DELIMITER_MARKER)) {
            String[] delimNumbersSplit = numbers.split("\n", 2);
            String[] delimiters = delimNumbersSplit[0]
                    .substring(DELIMITER_MARKER.length())
                    .split(DELIMITERS_DELIMITER);
            actualNumbers = delimNumbersSplit[1];
            StringBuilder delimiterBuilder = new StringBuilder(quote(delimiters[0]));
            for (int index = 1; index < delimiters.length; index++) {
                delimiterBuilder.append("|").append(quote(delimiters[index]));
            }
            delimiter = delimiterBuilder.toString();
        } else {
            actualNumbers = numbers;
            delimiter = DEFAULT_DELIMITER;
        }

        return actualNumbers.split(delimiter);
    }
}
