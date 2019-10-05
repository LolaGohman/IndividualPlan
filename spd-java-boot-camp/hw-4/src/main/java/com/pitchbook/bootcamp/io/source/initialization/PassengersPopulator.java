package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class PassengersPopulator implements DbInitializer {

    private String resourcePath;

    public PassengersPopulator(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void apply(TaxiParkDb taxiParkDb) {
        Set<Passenger> passengers;
        try {
            passengers = readPassengers();
            for (Passenger passenger : passengers) {
                taxiParkDb.addPassenger(passenger);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Passenger> readPassengers() throws IOException, ClassNotFoundException {
        Path source = Paths.get(resourcePath);
        Set<Passenger> passengers = new HashSet<>();
        try (InputStream is = Files.newInputStream(source);
             BufferedInputStream bis = new BufferedInputStream(is);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            passengers = (Set<Passenger>) ois.readObject();
        } catch (EOFException ignored) {
        }
        return passengers;
    }

}