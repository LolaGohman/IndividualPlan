package com.pitchbook.bootcamp.io.model;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class Trip implements Serializable {

    private static final long serialVersionUID = -5229380833234915039L;

    private final Driver driver;

    private final Set<Passenger> passengers;

    private final int duration;

    private final double distance;

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

    @Override
    public String toString() {
        return "Trip{" +
                "driver=" + driver +
                ", passengers=" + passengers +
                ", duration=" + duration +
                ", distance=" + distance +
                ", discount=" + discount +
                "} \n";
    }

    /**
     * @return the total cost of the trip
     */
    @Transient
    public double getCost() {
        double discountValue = discount == null ? 0.0 : discount;
        return (1 - discountValue) * (duration + distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return duration == trip.duration &&
                Double.compare(trip.distance, distance) == 0 &&
                Objects.equals(driver, trip.driver) &&
                Objects.equals(passengers, trip.passengers) &&
                Objects.equals(discount, trip.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, passengers, duration, distance, discount);
    }
}
