package com.pitchbook.bootcamp.io.source.db.repository.passenger;

import com.pitchbook.bootcamp.io.model.Passenger;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface PassengerRepository {

    Set<Passenger> getAllPassengers() throws SQLException;

    Optional<Passenger> findPassenger(String name) throws SQLException;

    void addPassenger(Passenger passenger) throws SQLException;
}
