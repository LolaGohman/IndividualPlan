package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.source.db.repository.driver.DriverRepository;
import com.pitchbook.bootcamp.io.source.db.repository.driver.DriverRepositoryImpl;
import com.pitchbook.bootcamp.io.source.db.service.driver.DriverService;
import com.pitchbook.bootcamp.io.source.db.service.driver.DriverServiceImpl;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import javax.sql.DataSource;
import java.nio.file.Paths;

public class V5__Insert_drivers_on_init extends BaseJavaMigration {

    private static final String DRIVERS_RESOURCE = "com/pitchbook/bootcamp/io/data/drivers.dat";

    @Override
    public void migrate(Context context) throws Exception {
        String driversPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource(DRIVERS_RESOURCE).toURI()).toString();
        FileDataPopulator populator = new FileDataPopulator(driversPath);

        DataSource dataSource = context.getConfiguration().getDataSource();
        DriverRepository driverRepository = new DriverRepositoryImpl(dataSource);
        DriverService driverService = new DriverServiceImpl(driverRepository);

        for (Object driver : populator.readCollectionFromFile()) {
            driverService.addDriver((Driver) driver);
        }
    }

}
