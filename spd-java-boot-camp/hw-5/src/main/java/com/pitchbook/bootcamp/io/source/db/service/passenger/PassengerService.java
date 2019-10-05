package com.pitchbook.bootcamp.io.source.db.service.passenger;

import com.pitchbook.bootcamp.io.model.Passenger;

import java.util.Optional;
import java.util.Set;

public interface PassengerService {

    Set<Passenger> getAllPassengers();

    Optional<Passenger> findPassenger(String name);

    void addPassenger(Passenger passenger);

}
