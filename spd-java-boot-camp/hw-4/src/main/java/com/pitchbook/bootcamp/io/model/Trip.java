package com.pitchbook.bootcamp.io.model;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Set;

public class Trip implements Serializable {

    private static final long serialVersionUID = -5229380833234915039L;

    private  Driver driver;

    private  Set<Passenger> passengers;

    private  int duration;

    private  double distance;

    private  Double discount;

    public Trip(){

    }

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

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDiscount(Double discount) {
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
}
