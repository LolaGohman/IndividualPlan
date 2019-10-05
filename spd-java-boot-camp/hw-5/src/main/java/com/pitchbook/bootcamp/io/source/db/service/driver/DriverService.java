package com.pitchbook.bootcamp.io.source.db.service.driver;

import com.pitchbook.bootcamp.io.model.Driver;
import java.util.Optional;
import java.util.Set;

public interface DriverService {

    Set<Driver> getAllDrivers();

    Optional<Driver> findDriver(String name);

    void addDriver(Driver driver);

}
