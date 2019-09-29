package com.pitchbook.bootcamp.io.source.db;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.TaxiPark;
import com.pitchbook.bootcamp.io.model.Trip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FileBackedTaxiParkDb implements TaxiParkDb {

    private String dbFilePath;

    public FileBackedTaxiParkDb(String dbFilePath) {
        this.dbFilePath = dbFilePath;
        try {
            writeTaxiParkToDatabase(readDatabase(dbFilePath), dbFilePath);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addDriver(Driver driver) {
        if (findDriver(driver.getName()).isEmpty()) {
            try {
                addDriverToDriverPark(driver, dbFilePath);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Set<Driver> getAllDrivers() {
        try {
            return readDatabase(dbFilePath).getAllDrivers();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Driver> findDriver(String name) {
        for (Driver driver : getAllDrivers()) {
            if (driver.getName().equals(name)) {
                return Optional.of(driver);
            }
        }
        return Optional.empty();
    }

    @Override
    public void addPassenger(Passenger passenger) {
        try {
            addPassengerToDriverPark(passenger, dbFilePath);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Passenger> findPassenger(String name) {
        for (Passenger passenger : getAllPassengers()) {
            if (passenger.getName().equals(name)) {
                return Optional.of(passenger);
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<Passenger> getAllPassengers() {
        Set<Passenger> passengers;
        try {
            passengers = new HashSet<>(readDatabase(dbFilePath).getAllPassengers());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return passengers;
    }

    @Override
    public List<Trip> getAllTrips() {
        try {
            return readDatabase(dbFilePath).getAllTrips();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTrip(Trip trip) {
        try {
            addTripToDriverPark(trip, dbFilePath);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPassengerToDriverPark(Passenger passenger, String dbFilePath) throws IOException, ClassNotFoundException {
        TaxiPark taxiPark = readDatabase(dbFilePath);
        taxiPark.getAllPassengers().add(passenger);
        writeTaxiParkToDatabase(taxiPark, dbFilePath);
    }

    private void addDriverToDriverPark(Driver driver, String dbFilePath) throws IOException, ClassNotFoundException {
        TaxiPark taxiPark = readDatabase(dbFilePath);
        taxiPark.getAllDrivers().add(driver);
        writeTaxiParkToDatabase(taxiPark, dbFilePath);
    }

    private void addTripToDriverPark(Trip trip, String dbFilePath) throws IOException, ClassNotFoundException {
        TaxiPark taxiPark = readDatabase(dbFilePath);
        taxiPark.getAllTrips().add(trip);
        writeTaxiParkToDatabase(taxiPark, dbFilePath);
    }

    private void writeTaxiParkToDatabase(TaxiPark taxiPark, String dbFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(dbFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream ous = new ObjectOutputStream(bos)) {
            ous.writeObject(taxiPark);
        }
    }

    private TaxiPark readDatabase(String databaseUrl) throws IOException, ClassNotFoundException {
        TaxiPark taxiPark = new TaxiPark(new HashSet<>(), new HashSet<>(), new ArrayList<>());
        try (FileInputStream fis = new FileInputStream(databaseUrl);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            taxiPark = (TaxiPark) ois.readObject();
        } catch (EOFException ignored) {
        }
        return taxiPark;
    }
}