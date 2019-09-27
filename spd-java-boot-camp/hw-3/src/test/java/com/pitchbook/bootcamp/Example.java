package com.pitchbook.bootcamp;

import java.util.Objects;

public class Example {

    private String name;

    Example() {
        this.name = "test";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example test = (Example) o;
        return Objects.equals(name, test.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
