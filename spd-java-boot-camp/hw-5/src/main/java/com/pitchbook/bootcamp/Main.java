package com.pitchbook.bootcamp;

import com.pitchbook.bootcamp.io.analytics.TaxiParkDbAnalytic;
import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.source.db.ConnectionPool;
import com.pitchbook.bootcamp.io.source.db.RelationalTaxiParkDb;
import com.pitchbook.bootcamp.io.source.db.repository.driver.DriverRepository;
import com.pitchbook.bootcamp.io.source.db.repository.driver.DriverRepositoryImpl;
import com.pitchbook.bootcamp.io.source.db.repository.passenger.PassengerRepository;
import com.pitchbook.bootcamp.io.source.db.repository.passenger.PassengerRepositoryImpl;
import com.pitchbook.bootcamp.io.source.db.repository.trip.TripRepository;
import com.pitchbook.bootcamp.io.source.db.repository.trip.TripRepositoryImpl;
import com.pitchbook.bootcamp.io.source.db.service.driver.DriverService;
import com.pitchbook.bootcamp.io.source.db.service.driver.DriverServiceImpl;
import com.pitchbook.bootcamp.io.source.db.service.passenger.PassengerService;
import com.pitchbook.bootcamp.io.source.db.service.passenger.PassengerServiceImpl;
import com.pitchbook.bootcamp.io.source.db.service.trip.TripService;
import com.pitchbook.bootcamp.io.source.db.service.trip.TripServiceImpl;
import com.pitchbook.bootcamp.util.NotImplementedError;
import org.flywaydb.core.Flyway;
import javax.sql.DataSource;
import java.util.Map;
import static java.util.Map.entry;

public class Main {

    //generatedata.com->sql-> generate sql

    public static void main(String[] args) {

        DataSource dataSource = ConnectionPool.getInstance();

        Flyway flyway = Flyway
                .configure()
                .locations("com/pitchbook/bootcamp/io/source/initialization", "db/migration")
                .dataSource(ConnectionPool.getInstance()).load();

        flyway.migrate();

        TripRepository tripRepository = new TripRepositoryImpl(dataSource);
        PassengerRepository passengerRepository = new PassengerRepositoryImpl(dataSource);
        DriverRepository driverRepository = new DriverRepositoryImpl(dataSource);

        TripService tripService = new TripServiceImpl(tripRepository);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        PassengerService passengerService = new PassengerServiceImpl(passengerRepository);

        RelationalTaxiParkDb relationalTaxiParkDb = new RelationalTaxiParkDb(driverService, passengerService, tripService);

        TaxiParkDbAnalytic analytics = new TaxiParkDbAnalytic(relationalTaxiParkDb);
        Map<Driver, Integer> driversEarnings = analytics.calculateDriversEarnings();

        Map<Driver, Integer> expectedEarnings = Map.ofEntries(
                entry(new Driver("Uberto"), 681),
                entry(new Driver("Gonzalo"), 363),
                entry(new Driver("Giovani"), 886),
                entry(new Driver("Mario"), 792)
        );

        if (!driversEarnings.equals(expectedEarnings)) {
            throw new NotImplementedError("Task not completed");
        }
    }


}
