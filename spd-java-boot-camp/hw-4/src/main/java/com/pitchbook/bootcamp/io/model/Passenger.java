package com.pitchbook.bootcamp.io.model;

import java.io.Serializable;

public class Passenger implements Serializable {

    private static final long serialVersionUID = 2079921069070819788L;

    private  String name;

    public Passenger(){

    }

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
