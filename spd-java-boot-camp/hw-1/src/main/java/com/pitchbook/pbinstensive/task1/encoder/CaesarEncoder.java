package com.pitchbook.pbinstensive.task1.encoder;

import com.pitchbook.pbinstensive.task1.PropertyReader;
import com.pitchbook.pbinstensive.task1.exception.PropertyDoesNotExistException;

public class CaesarEncoder implements Encoder {

    private static final String CAESAR_PROPERTIES_FILE_NAME = "caesar.properties";

    @Override
    public String encode(String input) {
        if (input.isBlank() || input.length() > 20) {
            throw new IllegalArgumentException("Please, enter correct value!");
        } else {
            StringBuilder resultString = new StringBuilder();
            String[] inputLetters = input.strip().split("");
            for (String letter : inputLetters) {
                if (letter.isBlank()) {
                    resultString.append(" ");
                } else if(Character.isLetter(letter.charAt(0))){
                    try {
                        String symbolToAppend = PropertyReader.readPropertyValueByKey(CAESAR_PROPERTIES_FILE_NAME,
                                letter.toUpperCase());
                        if (Character.isLowerCase(letter.charAt(0))) {
                            resultString.append(symbolToAppend.toLowerCase());
                        } else {
                            resultString.append(symbolToAppend);
                        }
                    } catch (PropertyDoesNotExistException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    resultString.append(letter);
                }
            }
            return resultString.toString();
        }
    }

}

