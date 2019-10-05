package com.pitchbook.bootcamp.io.source.db.service.passenger;

import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.source.db.repository.passenger.PassengerRepository;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Set<Passenger> getAllPassengers() {
        try {
            return passengerRepository.getAllPassengers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Passenger> findPassenger(String name) {
        try {
            return passengerRepository.findPassenger(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPassenger(Passenger passenger) {
        if (findPassenger(passenger.getName()).isEmpty()) {
            try {
                passengerRepository.addPassenger(passenger);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
