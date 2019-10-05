package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TripsPopulator implements DbInitializer {

    private String resourcePath;

    public TripsPopulator(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void apply(TaxiParkDb taxiParkDb) {
        List<Trip> trips;
        try {
            trips = readTrips();
            for (Trip trip : trips) {
                taxiParkDb.addTrip(trip);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Trip> readTrips() throws IOException, ClassNotFoundException {
        Path source = Paths.get(resourcePath);
        List<Trip> trips = new ArrayList<>();
        try (InputStream is = Files.newInputStream(source) ;
             BufferedInputStream bis = new BufferedInputStream(is);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
             trips = (List<Trip>) ois.readObject();
        } catch (EOFException ignored) {
        }
        return trips;
    }

}