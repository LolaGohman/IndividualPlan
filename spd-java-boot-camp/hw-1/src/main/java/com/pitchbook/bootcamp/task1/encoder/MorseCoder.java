package com.pitchbook.bootcamp.task1.encoder;

import com.pitchbook.bootcamp.task1.exception.PropertyDoesNotExistException;

import java.util.AbstractMap;
import java.util.Map;

public class MorseCoder implements Encoder {

    private static final Map<String, String> morseSymbols = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("A", ".-"),
            new AbstractMap.SimpleEntry<>("B", "-..."),
            new AbstractMap.SimpleEntry<>("C", "-.-."),
            new AbstractMap.SimpleEntry<>("D", "-.."),
            new AbstractMap.SimpleEntry<>("E", "."),
            new AbstractMap.SimpleEntry<>("F", "..-."),
            new AbstractMap.SimpleEntry<>("G", "--."),
            new AbstractMap.SimpleEntry<>("H", "...."),
            new AbstractMap.SimpleEntry<>("I", ".."),
            new AbstractMap.SimpleEntry<>("J", ".---"),
            new AbstractMap.SimpleEntry<>("K", "-.-"),
            new AbstractMap.SimpleEntry<>("L", ".-.."),
            new AbstractMap.SimpleEntry<>("M", "--"),
            new AbstractMap.SimpleEntry<>("N", "-."),
            new AbstractMap.SimpleEntry<>("O", "---"),
            new AbstractMap.SimpleEntry<>("P", ".--."),
            new AbstractMap.SimpleEntry<>("Q", "--.-"),
            new AbstractMap.SimpleEntry<>("R", ".-."),
            new AbstractMap.SimpleEntry<>("S", "..."),
            new AbstractMap.SimpleEntry<>("T", "-"),
            new AbstractMap.SimpleEntry<>("U", "..-"),
            new AbstractMap.SimpleEntry<>("V", "...-"),
            new AbstractMap.SimpleEntry<>("W", ".--"),
            new AbstractMap.SimpleEntry<>("X", "-..-"),
            new AbstractMap.SimpleEntry<>("Y", "-.--"),
            new AbstractMap.SimpleEntry<>("Z", "--.."),
            new AbstractMap.SimpleEntry<>("1", ".----"),
            new AbstractMap.SimpleEntry<>("2", "..---"),
            new AbstractMap.SimpleEntry<>("3", "...--"),
            new AbstractMap.SimpleEntry<>("4", "....-"),
            new AbstractMap.SimpleEntry<>("5", "....."),
            new AbstractMap.SimpleEntry<>("6", "-...."),
            new AbstractMap.SimpleEntry<>("7", "--..."),
            new AbstractMap.SimpleEntry<>("8", "---.."),
            new AbstractMap.SimpleEntry<>("9", "----."),
            new AbstractMap.SimpleEntry<>("0", "-----"),
            new AbstractMap.SimpleEntry<>(" ", " ")
    );

    private static final String MORSE_SYMBOL_SEPARATOR = " ";
    private static final String MORSE_WORD_SEPARATOR = "/";
    private static final String WORD_SEPARATOR = " ";

    @Override
    public String encode(String input) {
        if (input == null || input.isBlank() || input.length() > 20) {
            throw new IllegalArgumentException("Please, enter correct value!");
        } else {
            StringBuilder resultString = new StringBuilder();
            String[] inputWords = input.strip().split(WORD_SEPARATOR);
            for (String word : inputWords) {
                char[] wordLetters = word.toCharArray();
                for (char letter : wordLetters) {
                    try {
                        resultString.append(encodeMorseLetter(letter));
                        resultString.append(MORSE_SYMBOL_SEPARATOR);
                    } catch (PropertyDoesNotExistException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
                resultString.append(MORSE_WORD_SEPARATOR);
            }
            return resultString.toString();
        }
    }

    private String encodeMorseLetter(char inputChar) throws PropertyDoesNotExistException {
        if (Character.isDigit(inputChar) || Character.isLetter(inputChar)) {
            if (morseSymbols.get(String.valueOf(inputChar).toUpperCase()) != null) {
                return morseSymbols.get(String.valueOf(inputChar).toUpperCase());
            }
            throw new PropertyDoesNotExistException("Symbol: '" + inputChar + "' is not supported!");
        } else {
            return "";
        }
    }

    @Override
    public String decode(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Please, enter correct value!");
        } else {
            StringBuilder resultString = new StringBuilder();
            String[] inputWords = input.strip().split(MORSE_WORD_SEPARATOR);
            for (String word : inputWords) {
                String[] morseLetters = word.split(MORSE_SYMBOL_SEPARATOR);
                for (String letter : morseLetters) {
                    try {
                        resultString.append(decodeMorseLetter(letter));
                    } catch (PropertyDoesNotExistException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
                resultString.append(WORD_SEPARATOR);
            }
            return resultString.toString();
        }
    }

    private char decodeMorseLetter(String input) throws PropertyDoesNotExistException {
        if (input.isBlank()) {
            return ' ';
        } else {
            for (Map.Entry<String, String> entry : morseSymbols.entrySet()) {
                if (entry.getValue().equals(input)) {
                    return entry.getKey().charAt(0);
                }
            }
        }
        throw new PropertyDoesNotExistException("Morse symbol: '" + input + "' does not exist!");
    }

}

