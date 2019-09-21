package com.pitchbook.bootcamp.task1.encoder;

import com.pitchbook.bootcamp.task1.exception.PropertyDoesNotExistException;

import java.util.Arrays;
import java.util.List;

public class CaesarCoder implements Encoder {

    private final int key;

    private static final List<String> supportedSymbols = Arrays.asList("A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    public CaesarCoder() {
        this(3);
    }

    CaesarCoder(int key) {
        this.key = key;
    }

    @Override
    public String encode(String input) {
        return codeString(input, key);
    }

    @Override
    public String decode(String input) {
        return codeString(input, -key);
    }

    private String codeString(String input, int key) {
        if (input == null || input.isBlank() || input.length() > 20) {
            throw new IllegalArgumentException("Please, enter correct value!");
        } else {
            StringBuilder resultString = new StringBuilder();
            char[] inputLetters = input.strip().toCharArray();
            for (char letter : inputLetters) {
                if (Character.isLetter(letter)) {
                    try {
                        resultString.append(codeChar(letter, key));
                    } catch (PropertyDoesNotExistException e) {
                        throw new IllegalArgumentException(e);
                    }
                } else {
                    resultString.append(letter);
                }
            }
            return resultString.toString();
        }
    }

    private char codeChar(char c, int key) throws PropertyDoesNotExistException {
        final int supportedCharsLength = supportedSymbols.size();
        final int index = supportedSymbols.indexOf(String.valueOf(c).toUpperCase());
        if (index >= 0) {
            final int shiftedIndex = (index + (key % supportedCharsLength) + supportedCharsLength) % supportedCharsLength;
            if (Character.isLowerCase(c)) {
                return supportedSymbols.get(shiftedIndex).toLowerCase().charAt(0);
            }
            return supportedSymbols.get(shiftedIndex).charAt(0);
        } else {
            throw new PropertyDoesNotExistException("Unsupported input character " + c);
        }
    }

}

