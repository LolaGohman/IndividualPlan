package com.pitchbook.bootcamp.io.model;

import java.io.Serializable;
import java.util.Objects;

public class Passenger implements Serializable {

    private static final long serialVersionUID = 2079921069070819788L;

    private final String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
