package com.pitchbook.bootcamp.io.source.db;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

class AppendableOutputStream extends ObjectOutputStream {

    AppendableOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        reset();
    }

}
