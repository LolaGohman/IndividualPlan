package com.pitchbook.bootcamp.task1.factory;

import com.pitchbook.bootcamp.task1.encoder.CaesarCoder;
import com.pitchbook.bootcamp.task1.encoder.Encoder;
import com.pitchbook.bootcamp.task1.EncodingType;
import com.pitchbook.bootcamp.task1.encoder.MorseCoder;
import java.util.Map;

public class Encoders {

    private static final Map<EncodingType, Encoder> encoders = Map.of(
            EncodingType.CAESAR, new CaesarCoder(),
            EncodingType.MORSE, new MorseCoder()
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
