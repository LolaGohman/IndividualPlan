package com.pitchbook.bootcamp.collections.taxipark;

import java.util.Set;

public class Trip {

    private final Driver driver;

    private final Set<Passenger> passengers;

    /**
     * the trip duration in minutes
     */
    private final int duration;

    /**
     * the trip distance in km
     */
    private final double distance;

    /**
     * the percentage of discount (in range [0.0 to 1.0] if not null)
     */
    private final Double discount;

    public Trip(
        Driver driver,
        Set<Passenger> passengers,
        int duration,
        double distance,
        Double discount
    ) {
        this.driver = driver;
        this.passengers = passengers;
        this.duration = duration;
        this.distance = distance;
        this.discount = discount;
    }

    public Driver getDriver() {
        return driver;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public int getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public Double getDiscount() {
        return discount;
    }

    /**
     * @return the total cost of the trip
     */
    public double getCost() {
        double discountValue = discount == null ? 0.0 : discount;
        return (1 - discountValue) * (duration + distance);
    }
}
