package com.pitchbook.bootcamp.collections;

import com.pitchbook.bootcamp.collections.taxipark.Passenger;
import com.pitchbook.bootcamp.collections.taxipark.TaxiPark;
import com.pitchbook.bootcamp.collections.taxipark.Trip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaxiParkTransformMethods {

    public static <T> Collection<T> toUpperCase(Collection<T> collection) {
        List<T> resultList = collection == null ? new ArrayList<>() : new ArrayList<>(collection);
        for (int i = 0; i < resultList.size(); i++) {
            T element = resultList.get(i);
            if (element instanceof String) {
                resultList.set(i, (T) ((String) element).toUpperCase());
            }
        }
        return resultList;
    }

    public static <T> Collection<T> toLoverCase(Collection<T> collection) {
        List<T> resultList = collection == null ? new ArrayList<>() : new ArrayList<>(collection);
        for (int i = 0; i < resultList.size(); i++) {
            T element = resultList.get(i);
            if (element instanceof String) {
                resultList.set(i, (T) ((String) element).toLowerCase());
            }
        }
        return resultList;
    }

    public static <T> Set<T> leaveUniqueElements(Collection<T> collection) {
       return collection == null? new HashSet<>(): new HashSet<>(collection);
    }

    public static <K, V> Map<V, K> changeRelation(Map<K, V> map) {
        Map<V, K> resultMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            resultMap.put(entry.getValue(), entry.getKey());
        }
        return resultMap;
    }

    public static Set<Trip> findPassengerTrips(TaxiPark taxiPark, Passenger passenger) {
        Set<Trip> passengerTrips = new HashSet<>();
        for (Trip trip : taxiPark.getAllTrips()) {
            for (Passenger p : trip.getPassengers()) {
                if (p.equals(passenger)) {
                    passengerTrips.add(trip);
                    break;
                }
            }
        }
        return passengerTrips;
    }

    public static Map<Passenger, Integer> passengerToTripsCount(TaxiPark taxiPark) {
        Map<Passenger, Integer> resultMap = new HashMap<>();
        for (Passenger passenger : taxiPark.getAllPassengers()) {
            int amountOfTrips = FaithfulPassengers.amountOfCompletedTripsByPassenger(taxiPark.getAllTrips(), passenger);
            resultMap.put(passenger, amountOfTrips);
        }
        return resultMap;
    }

}
