package com.pitchbook.bootcamp.task1;

public enum EncodingType {
    MORSE, CAESAR;

    public static EncodingType findEncoding(String encodingName) {
        if (encodingName != null) {
            for (EncodingType eType : values()) {
                if (eType.name().equalsIgnoreCase(encodingName.strip())) {
                    return eType;
                }
            }
        }
        throw new IllegalArgumentException("Action not recognized: " + encodingName);
    }

}
