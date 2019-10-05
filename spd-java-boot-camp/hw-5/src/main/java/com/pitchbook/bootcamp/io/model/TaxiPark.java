package com.pitchbook.bootcamp.io.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TaxiPark implements Serializable {

    private static final long serialVersionUID = -665L;

    private final Set<Driver> allDrivers;
    private final Set<Passenger> allPassengers;
    private final List<Trip> allTrips;

    public TaxiPark(
            Set<Driver> allDrivers,
            Set<Passenger> allPassengers,
            List<Trip> allTrips
    ) {
        this.allDrivers = allDrivers;
        this.allPassengers = allPassengers;
        this.allTrips = allTrips;
    }

    public Set<Driver> getAllDrivers() {
        return allDrivers;
    }

    public Set<Passenger> getAllPassengers() {
        return allPassengers;
    }

    public List<Trip> getAllTrips() {
        return allTrips;
    }

    @Override
    public String toString() {
        return "TaxiPark{" +
                "allDrivers=" + allDrivers +
                ", allPassengers=" + allPassengers +
                ", allTrips=" + allTrips +
                '}';
    }

}
