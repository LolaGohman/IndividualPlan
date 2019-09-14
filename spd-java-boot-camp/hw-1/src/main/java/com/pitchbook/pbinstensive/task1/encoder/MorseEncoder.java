package com.pitchbook.pbinstensive.task1.encoder;

import com.pitchbook.pbinstensive.task1.PropertyReader;
import com.pitchbook.pbinstensive.task1.exception.PropertyDoesNotExistException;

public class MorseEncoder implements Encoder {

    private static final String MORSE_PROPERTIES_FILE_NAME = "morse.properties";
    private static final String SYMBOL_SEPARATOR = " ";
    private static final String WORD_SEPARATOR = "  ";

    @Override
    public String encode(String input) {
        if (input.isBlank() || input.length() > 20) {
            throw new IllegalArgumentException("Please, enter correct value!");
        } else {
            StringBuilder resultString = new StringBuilder();
            String[] inputLetters = input.strip().split("");
            for (String letter : inputLetters) {
                if (letter.isBlank()) {
                    resultString.append(WORD_SEPARATOR);
                } else if(Character.isDigit(letter.charAt(0))||Character.isLetter(letter.charAt(0))){
                    try {
                        resultString.append(PropertyReader.readPropertyValueByKey(MORSE_PROPERTIES_FILE_NAME,
                                letter.toUpperCase()));
                    } catch (PropertyDoesNotExistException e) {
                        throw new RuntimeException(e);
                    }
                    resultString.append(SYMBOL_SEPARATOR);
                }
            }
            return resultString.toString();
        }
    }

}

