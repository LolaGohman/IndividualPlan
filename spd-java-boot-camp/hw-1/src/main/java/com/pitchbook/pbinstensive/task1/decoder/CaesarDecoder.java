package com.pitchbook.pbinstensive.task1.decoder;

import com.pitchbook.pbinstensive.task1.PropertyReader;
import com.pitchbook.pbinstensive.task1.exception.PropertyDoesNotExistException;

public class CaesarDecoder implements Decoder {

    private static final String CAESAR_PROPERTIES_FILE_NAME = "caesar.properties";

    @Override
    public String decode(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("Please, enter correct value!");
        } else {
            StringBuilder resultString = new StringBuilder();
            String[] inputLetters = input.strip().split("");
            for (String letter : inputLetters) {
                if (letter.isBlank()) {
                    resultString.append(" ");
                } else if (Character.isLetter(letter.charAt(0))) {
                    try {
                        String symbolToAppend = PropertyReader.readPropertyKeyByValue(CAESAR_PROPERTIES_FILE_NAME,
                                letter.toUpperCase());
                        if (Character.isLowerCase(letter.charAt(0))) {
                            resultString.append(symbolToAppend.toLowerCase());
                        } else {
                            resultString.append(symbolToAppend);
                        }
                    } catch (PropertyDoesNotExistException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    resultString.append(letter);
                }
            }
            return resultString.toString();
        }
    }

}
