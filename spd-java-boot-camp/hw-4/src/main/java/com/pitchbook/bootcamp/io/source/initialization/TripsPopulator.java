package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

    public List<Trip> readTrips() throws IOException, ClassNotFoundException {
        File source = new File(resourcePath);
        List<Trip> trips = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(source);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            trips = (List<Trip>) ois.readObject();
        } catch (EOFException ignored) {
        }
        return trips;
    }

}
