package com.pitchbook.bootcamp.collections;

import com.pitchbook.bootcamp.collections.taxipark.Passenger;
import com.pitchbook.bootcamp.collections.taxipark.TaxiPark;
import com.pitchbook.bootcamp.collections.taxipark.Trip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FaithfulPassengers {

    /**
     * Find all the clients who completed at least the given number of trips.
     */

    public static Set<Passenger> findFaithfulPassengers(TaxiPark taxiPark, int minTrips) {
        Set<Passenger> faithfulPassengers = new HashSet<>();
        for (Passenger passenger : taxiPark.getAllPassengers()) {
            if (amountOfCompletedTripsByPassenger(taxiPark.getAllTrips(), passenger) >= minTrips) {
                faithfulPassengers.add(passenger);
            }
        }
        return faithfulPassengers;
    }

    static int amountOfCompletedTripsByPassenger(List<Trip> trips, Passenger passenger) {
        int amountOfCompletedTrips = 0;
        for (Trip trip : trips) {
            for (Passenger p : trip.getPassengers()) {
                if (p.equals(passenger)) {
                    amountOfCompletedTrips++;
                }
            }
        }
        return amountOfCompletedTrips;
    }

}
