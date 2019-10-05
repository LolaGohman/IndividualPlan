package com.pitchbook.bootcamp.io.source.initialization;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

class FileDataPopulator {

    private String resourcePath;

    FileDataPopulator(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    <T> Collection<T> readCollectionFromFile() throws IOException, ClassNotFoundException {
        Path source = Paths.get(resourcePath);
        Collection<T> resultList = null;
        try (InputStream is = Files.newInputStream(source);
             BufferedInputStream bis = new BufferedInputStream(is);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            resultList = (Collection<T>) ois.readObject();
        } catch (EOFException ignored) {
        }
        return resultList;
    }

}