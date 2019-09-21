package com.pitchbook.bootcamp.collections;

import com.pitchbook.bootcamp.collections.taxipark.Driver;
import com.pitchbook.bootcamp.collections.taxipark.TaxiPark;
import com.pitchbook.bootcamp.collections.taxipark.Trip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakeDrivers {

    /**
     * Find all the drivers who didn't perform any trips.
     */
    public static Set<Driver> findAllDriversWhoDidntPerformAnyTrips(TaxiPark taxiPark) {
        Set<Driver> fakeDrivers = new HashSet<>();
        for (Driver driver : taxiPark.getAllDrivers()) {
            if(!checkIfDriverPerfomedAtLeastOneTrip(driver, taxiPark.getAllTrips())){
                fakeDrivers.add(driver);
            }
        }
        return fakeDrivers;
    }

    private static boolean checkIfDriverPerfomedAtLeastOneTrip(Driver driver, List<Trip> trips) {
        for (Trip trip : trips) {
            if (trip.getDriver().equals(driver)) {
                return true;
            }
        }
        return false;
    }
}
