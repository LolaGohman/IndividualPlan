package com.pitchbook.bootcamp.io.source.db.repository.passenger;

import com.pitchbook.bootcamp.io.model.Passenger;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PassengerRepositoryImpl implements PassengerRepository {

    private static final String SELECT_ALL_PASSENGERS = "select * from passengers";
    private static final String INSERT_PASSENGER = "insert into passengers(name) values(?)";
    private static final String FIND_PASSENGER = "select * from passengers where name = ?";

    private final DataSource dataSource;

    public PassengerRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<Passenger> getAllPassengers() throws SQLException {
        Set<Passenger> passengers = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PASSENGERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    passengers.add(new Passenger(resultSet.getString("name")));
                }
            }
        }
        return passengers;
    }

    @Override
    public Optional<Passenger> findPassenger(String name) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PASSENGER)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                Passenger passenger = null;
                while (resultSet.next()) {
                    passenger = new Passenger(resultSet.getString("name"));
                }
                return Optional.ofNullable(passenger);
            }
        }
    }

    @Override
    public void addPassenger(Passenger passenger) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PASSENGER)) {
            statement.setString(1, passenger.getName());
            statement.execute();
        }
    }

}
