package com.pitchbook.bootcamp.io.source.db.repository.driver;

import com.pitchbook.bootcamp.io.model.Driver;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface DriverRepository {

    Set<Driver> getAllDrivers() throws SQLException;

    Optional<Driver> findDriver(String name) throws SQLException;

    void addDriver(Driver driver) throws SQLException;

}
