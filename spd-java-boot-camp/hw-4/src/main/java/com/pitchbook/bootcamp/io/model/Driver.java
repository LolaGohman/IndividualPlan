package com.pitchbook.bootcamp.io.model;

import java.io.Serializable;
import java.util.Objects;

public class Driver implements Serializable {

    private static final long serialVersionUID = -6808516096934631227L;

    private final String name;

    public Driver(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(name, driver.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
