package com.pitchbook.bootcamp.io.source.db.service.driver;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.source.db.repository.driver.DriverRepository;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Set<Driver> getAllDrivers() {
        try {
            return driverRepository.getAllDrivers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Driver> findDriver(String name) {
        try {
            return driverRepository.findDriver(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addDriver(Driver driver) {
        if (findDriver(driver.getName()).isEmpty()) {
            try {
                driverRepository.addDriver(driver);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
