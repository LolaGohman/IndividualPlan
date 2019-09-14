package com.pitchbook.pbinstensive.task1.factory;

import com.pitchbook.pbinstensive.task1.decoder.Decoder;
import com.pitchbook.pbinstensive.task1.EncodingType;
import com.pitchbook.pbinstensive.task1.decoder.CaesarDecoder;
import com.pitchbook.pbinstensive.task1.decoder.MorseDecoder;
import java.util.Map;

public class Decoders {

    private static final Map<EncodingType, Decoder> decoders = Map.of(
            EncodingType.CAESAR, new CaesarDecoder(),
            EncodingType.MORSE, new MorseDecoder()
    );

    public static Decoder getDecoder(String name) {
        EncodingType type = EncodingType.findEncoding(name);
        Decoder decoder = decoders.get(type);
        if (decoder != null) {
            return decoder;
        }
        throw new IllegalArgumentException("Type not supported!");
    }
}
