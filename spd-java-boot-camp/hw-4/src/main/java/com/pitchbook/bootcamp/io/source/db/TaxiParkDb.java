package com.pitchbook.bootcamp.io.source.db;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.Trip;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaxiParkDb {

    void addDriver(Driver driver);

    Set<Driver> getAllDrivers();

    Optional<Driver> findDriver(String name);

    void addPassenger(Passenger passenger);

    Optional<Passenger> findPassenger(String name);

    Set<Passenger> getAllPassengers();

    void addTrip(Trip trip);

    List<Trip> getAllTrips();
}
