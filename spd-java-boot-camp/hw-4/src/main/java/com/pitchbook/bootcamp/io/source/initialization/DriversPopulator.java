package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

public class DriversPopulator implements DbInitializer {

    private String resourcePath;

    public DriversPopulator(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void apply(TaxiParkDb taxiParkDb) {
        Set<Driver> drivers;
        try {
            drivers = readDrivers();
            for (Driver driver : drivers) {
                taxiParkDb.addDriver(driver);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Driver> readDrivers() throws IOException, ClassNotFoundException {
        File source = new File(resourcePath);
        Set<Driver> drivers = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(source);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            drivers = (Set<Driver>) ois.readObject();
        } catch (EOFException ignored) {
        }
        return drivers;
    }

}
