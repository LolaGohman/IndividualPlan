package com.pitchbook.bootcamp.io.source.db;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.service.driver.DriverService;
import com.pitchbook.bootcamp.io.source.db.service.passenger.PassengerService;
import com.pitchbook.bootcamp.io.source.db.service.trip.TripService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RelationalTaxiParkDb implements TaxiParkDb {

    private final DriverService driverService;
    private final PassengerService passengerService;
    private final TripService tripService;

    public RelationalTaxiParkDb(DriverService driverService,
                                PassengerService passengerService,
                                TripService tripService) {
        this.driverService = driverService;
        this.passengerService = passengerService;
        this.tripService = tripService;
    }


    @Override
    public void addDriver(Driver driver) {
        driverService.addDriver(driver);
    }

    @Override
    public Set<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @Override
    public Optional<Driver> findDriver(String name) {
        return driverService.findDriver(name);
    }

    @Override
    public void addPassenger(Passenger passenger) {
        passengerService.addPassenger(passenger);
    }

    @Override
    public Optional<Passenger> findPassenger(String name) {
        return passengerService.findPassenger(name);
    }

    @Override
    public Set<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @Override
    public void addTrip(Trip trip) {
        tripService.addTrips(Collections.singletonList(trip));
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

}
