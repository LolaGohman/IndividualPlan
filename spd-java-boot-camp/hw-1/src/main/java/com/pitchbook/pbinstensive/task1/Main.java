package com.pitchbook.pbinstensive.task1;

import com.pitchbook.pbinstensive.task1.factory.Decoders;
import com.pitchbook.pbinstensive.task1.factory.Encoders;

public class Main {

    public static void main(String[] args) {
        readCommandLine(args);
    }

    private static void readCommandLine(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Please, enter both words: encoder name and phrase!");
        } else {
            String encoder = args[0];
            String phrase = args[1];
            String encodedSymbol = Encoders.getEncoder(encoder).encode(phrase);
            System.out.println("Phrase encoded by " + encoder.toUpperCase() + " is: '" + encodedSymbol);
            String decodedSymbol = Decoders.getDecoder(encoder).decode(encodedSymbol);
            System.out.println("Decoded phrase: " + decodedSymbol);
        }
    }

}
