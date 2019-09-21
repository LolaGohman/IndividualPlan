package com.pitchbook.bootcamp.task1;

import com.pitchbook.bootcamp.task1.factory.Encoders;

public class Main {

    private static final int indexOfEncoder = 0;
    private static final int indexOfPhrase = 1;

    public static void main(String[] args) {
        readCommandLine(args);
    }

    private static void readCommandLine(String[] args) {
        if (args==null||args.length != 2) {
            throw new IllegalArgumentException("Please, enter both words: encoder name and phrase!");
        } else {
            String encoder = args[indexOfEncoder];
            String phrase = args[indexOfPhrase];
            String encodedPhrase = Encoders.getEncoder(encoder).encode(phrase);
            System.out.println("Phrase encoded by " + encoder.toUpperCase() + " is: '" + encodedPhrase + "'");
            String decodedPhrase = Encoders.getEncoder(encoder).decode(encodedPhrase);
            System.out.println("Decoded phrase: " + decodedPhrase);
        }
    }

}

