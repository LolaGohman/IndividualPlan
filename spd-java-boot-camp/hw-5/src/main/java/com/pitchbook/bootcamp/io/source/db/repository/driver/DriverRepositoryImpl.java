package com.pitchbook.bootcamp.io.source.db.repository.driver;

import com.pitchbook.bootcamp.io.model.Driver;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DriverRepositoryImpl implements DriverRepository {

    private static final String SELECT_ALL_DRIVERS = "select * from drivers";
    private static final String INSERT_DRIVER = "insert into drivers(name) values(?)";
    private static final String FIND_DRIVER = "select * from drivers where name = ?";
    private final DataSource dataSource;

    public DriverRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<Driver> getAllDrivers() throws SQLException {
        Set<Driver> drivers = new HashSet<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DRIVERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    drivers.add(new Driver(resultSet.getString("name")));
                }
            }
        }
        return drivers;
    }

    @Override
    public Optional<Driver> findDriver(String name) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_DRIVER)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                Driver driver = null;
                while (resultSet.next()) {
                    driver = new Driver(resultSet.getString("name"));
                }
                return Optional.ofNullable(driver);
            }
        }
    }

    @Override
    public void addDriver(Driver driver) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_DRIVER)) {
            statement.setString(1, driver.getName());
            statement.execute();
        }
    }

}
