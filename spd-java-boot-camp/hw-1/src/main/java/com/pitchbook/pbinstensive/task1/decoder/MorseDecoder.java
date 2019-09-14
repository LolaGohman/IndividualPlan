package com.pitchbook.pbinstensive.task1.decoder;

import com.pitchbook.pbinstensive.task1.PropertyReader;
import com.pitchbook.pbinstensive.task1.exception.PropertyDoesNotExistException;

public class MorseDecoder implements Decoder {

    private static final String MORSE_PROPERTIES_FILE_NAME = "morse.properties";
    private static final String SYMBOL_SEPARATOR = " ";

    @Override
    public String decode(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("Please, enter correct value!");
        } else {
            StringBuilder resultString = new StringBuilder();
            String[] inputLetters = input.strip().split(SYMBOL_SEPARATOR);
            for (String letter : inputLetters) {
                if (letter.isBlank()) {
                    resultString.append(" ");
                } else {
                    try {
                        resultString.append(PropertyReader.readPropertyKeyByValue(MORSE_PROPERTIES_FILE_NAME,
                                letter.toUpperCase()));
                    } catch (PropertyDoesNotExistException e) {
                       throw new RuntimeException(e);
                    }
                }
            }
            return resultString.toString();
        }
    }

}
