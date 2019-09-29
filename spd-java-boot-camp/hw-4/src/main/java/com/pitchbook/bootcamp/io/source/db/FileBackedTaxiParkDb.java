package com.pitchbook.bootcamp.io.source.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.TaxiPark;
import com.pitchbook.bootcamp.io.model.Trip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FileBackedTaxiParkDb implements TaxiParkDb {

    private String dbFilePath;

    public FileBackedTaxiParkDb(String dbFilePath)  {
        this.dbFilePath = dbFilePath;
        try{
            if(readDatabase(dbFilePath)==null){
                initDatabase(new TaxiPark(new HashSet<>(), new HashSet<>(), new ArrayList<>()), dbFilePath);

            }else {
                initDatabase(readDatabase(dbFilePath), dbFilePath);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addDriver(Driver driver) {
        if (findDriver(driver.getName()).isEmpty()) {
            try {
                addDriverToDriverPark(driver, dbFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Set<Driver> getAllDrivers() {
        try {
            return readDatabase(dbFilePath).getAllDrivers();
        } catch (IOException e) {
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
        } catch (IOException e) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passengers;
    }

    @Override
    public List<Trip> getAllTrips() {
        try {
            return readDatabase(dbFilePath).getAllTrips();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTrip(Trip trip) {
        try {
            addTripToDriverPark(trip, dbFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPassengerToDriverPark(Passenger passenger, String dbFilePath) throws IOException {
        TaxiPark taxiPark = readDatabase(dbFilePath);
        taxiPark.getAllPassengers().add(passenger);
        initDatabase(taxiPark, dbFilePath);
    }

    private void addDriverToDriverPark(Driver driver, String dbFilePath) throws IOException {
        TaxiPark taxiPark = readDatabase(dbFilePath);
        taxiPark.getAllDrivers().add(driver);
        initDatabase(taxiPark, dbFilePath);
    }

    private void addTripToDriverPark(Trip trip, String dbFilePath) throws IOException {
        TaxiPark taxiPark = readDatabase(dbFilePath);
        taxiPark.getAllTrips().add(trip);
        initDatabase(taxiPark, dbFilePath);
    }

    private void initDatabase(TaxiPark taxiPark, String dbFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(dbFilePath), taxiPark);
    }

    private TaxiPark readDatabase(String databaseUrl) throws IOException {
        ObjectMapper mapper;
        TaxiPark taxiPark;
        try (InputStream inputStream = new FileInputStream(databaseUrl)) {
            mapper = new ObjectMapper();
            TypeReference<TaxiPark> reference = new TypeReference<>() {
            };
            try {
                taxiPark = mapper.readValue(inputStream, reference);
            }catch (Exception e){
                taxiPark = null;
            }

        }
        return taxiPark;
    }

}