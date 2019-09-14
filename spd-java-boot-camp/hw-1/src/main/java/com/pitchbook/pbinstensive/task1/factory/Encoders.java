package com.pitchbook.pbinstensive.task1.factory;

import com.pitchbook.pbinstensive.task1.encoder.CaesarEncoder;
import com.pitchbook.pbinstensive.task1.encoder.Encoder;
import com.pitchbook.pbinstensive.task1.EncodingType;
import com.pitchbook.pbinstensive.task1.encoder.MorseEncoder;

import java.util.Map;

public class Encoders {

    private static final Map<EncodingType, Encoder> encoders = Map.of(
            EncodingType.CAESAR, new CaesarEncoder(),
            EncodingType.MORSE, new MorseEncoder()
    );


    public static Encoder getEncoder(String name) {
        EncodingType type = EncodingType.findEncoding(name);
        Encoder encoder = encoders.get(type);
        if (encoder != null) {
            return encoder;
        }
        throw new IllegalArgumentException("Encoder: " + name+ " not supported!");
    }

}
