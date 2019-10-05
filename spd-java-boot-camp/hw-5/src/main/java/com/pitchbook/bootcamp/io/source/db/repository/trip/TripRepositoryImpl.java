package com.pitchbook.bootcamp.io.source.db.repository.trip;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.Trip;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripRepositoryImpl implements TripRepository {

    private static final String INSERT_TRIPS = "insert into trips(driver, duration, distance, discount ) values(?, ?, ?, ?) returning id";
    private static final String INSERT_INTO_TRIPS_PASSENGERS_TABLE = "insert into trips_passengers(trip, passenger) values(?, ?)";
    private static final String SELECT_FROM_TRIPS = "select * from trips";
    private static final String SELECT_PASSENGERS = "select passenger from trips_passengers where trip = ?";

    private final DataSource dataSource;

    public TripRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addTrips(List<Trip> trips) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement tripsStatement = connection.prepareStatement(INSERT_TRIPS);
                 PreparedStatement tripsPassengersStatement = connection.prepareStatement(INSERT_INTO_TRIPS_PASSENGERS_TABLE)) {
                for (Trip trip : trips) {
                    try (ResultSet set = prepareStatementWithTrip(tripsStatement, trip).executeQuery()) {
                        set.next();
                        tripsPassengersStatement.setInt(1, set.getInt("id"));
                        for (Passenger passenger : trip.getPassengers()) {
                            prepareStatementWithPassenger(tripsPassengersStatement, passenger);
                        }
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement prepareStatementWithTrip(PreparedStatement statement, Trip trip) throws SQLException {
        statement.setString(1, trip.getDriver().getName());
        statement.setInt(2, trip.getDuration());
        statement.setDouble(3, trip.getDistance());
        if (trip.getDiscount() == null) {
            statement.setObject(4, null);
        } else {
            statement.setDouble(4, trip.getDiscount());
        }
        return statement;
    }

    private void prepareStatementWithPassenger(PreparedStatement statement, Passenger passenger) throws SQLException {
        statement.setString(2, passenger.getName());
        statement.executeUpdate();
    }

    @Override
    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectAllTrips = connection.prepareStatement(SELECT_FROM_TRIPS)) {
            try (ResultSet set = selectAllTrips.executeQuery()) {
                while (set.next()) {
                    int tripId = set.getInt("id");
                    Set<Passenger> passengers = selectAllPassengersInTrip(tripId);
                    trips.add(new Trip(new Driver(set.getString("driver")),
                            passengers, set.getInt("duration"),
                            set.getDouble("distance"),
                            (Double) set.getObject("discount")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trips;
    }

    private Set<Passenger> selectAllPassengersInTrip(int tripId) {
        Set<Passenger> passengers = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PASSENGERS)) {
            statement.setInt(1, tripId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    passengers.add(new Passenger(resultSet.getString("passenger")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passengers;
    }

}


