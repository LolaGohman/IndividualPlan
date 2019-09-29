package com.pitchbook.bootcamp.io.model;

import java.util.List;
import java.util.Set;

public class TaxiPark {

    private Set<Driver> allDrivers;
    private Set<Passenger> allPassengers;
    private List<Trip> allTrips;

    public TaxiPark() {
    }

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

    public void setAllPassengers(Set<Passenger> allPassengers) {
        this.allPassengers = allPassengers;
    }

    public void setAllDrivers(Set<Driver> allDrivers) {
        this.allDrivers = allDrivers;
    }

    public void setAllTrips(List<Trip> allTrips) {
        this.allTrips = allTrips;
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
