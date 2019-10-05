package com.pitchbook.bootcamp.io;

import com.pitchbook.bootcamp.PropertyFileReader;
import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.RelationalTaxiParkDb;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
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
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class RelationalTaxiParkDbTest {

    private static final String PROPERTY_FILE_NAME = "application.properties";

    private static final String DEFAULT_DATABASE_URL = PropertyFileReader
            .readPropertyValueByKey(PROPERTY_FILE_NAME, "default.database.url");

    private static final String DEFAULT_USER = PropertyFileReader
            .readPropertyValueByKey(PROPERTY_FILE_NAME, "default.database.user");

    private static final String DEFAULT_PASSWORD = PropertyFileReader
            .readPropertyValueByKey(PROPERTY_FILE_NAME, "default.database.password");

    private static final String DRIVER = PropertyFileReader.readPropertyValueByKey(PROPERTY_FILE_NAME, "driver");

    private static final String TEST_DATABASE_NAME = "test" + System.currentTimeMillis();
    private static final String TEST_DATABASE_URL = "jdbc:postgresql://127.0.0.1:5432/" + TEST_DATABASE_NAME;

    private static final String DROP_DATABASE = "drop database " + TEST_DATABASE_NAME;
    private static final String CREATE_DATABASE = "create database " + TEST_DATABASE_NAME;

    private static final String PREVENT_NEW_CONNECTIONS = "UPDATE pg_database SET datallowconn = 'false'" +
            " WHERE datname = '" + TEST_DATABASE_NAME + "'";

    private static final String CLOSE_CURRENT_CONNECTIONS = "SELECT pg_terminate_backend(pg_stat_activity.pid)\n" +
            "FROM pg_stat_activity\n" +
            "WHERE pg_stat_activity.datname ='" + TEST_DATABASE_NAME + "' and pid <> pg_backend_pid()";

    private static final String TEST_DATABASE_DDL_LOCATION = "db/migration";

    private TaxiParkDb taxiParkDb;

    @Test
    public void driver_insertion_should_be_persistent() {
        final Driver mario = new Driver("Mario");
        taxiParkDb.addDriver(mario);
        boolean marioPersisted = taxiParkDb.findDriver("Mario").isPresent();
        assertTrue(marioPersisted);

        Set<Driver> allDrivers = taxiParkDb.getAllDrivers();
        assertEquals(allDrivers.stream().findFirst(), Optional.of(mario));
    }

    @Test
    public void passenger_insertion_should_be_persistent() {
        final Passenger laura = new Passenger("Laura");
        taxiParkDb.addPassenger(laura);
        boolean lauraPersisted = taxiParkDb.findPassenger("Laura").isPresent();
        assertTrue(lauraPersisted);

        Set<Passenger> allPassengers = taxiParkDb.getAllPassengers();

        assertEquals(allPassengers.stream().findFirst(), Optional.of(laura));
    }

    @Test
    public void trip_insertion_should_be_persistent() {
        taxiParkDb.addDriver(new Driver("Mario"));
        taxiParkDb.addPassenger(new Passenger("Laura"));

        taxiParkDb.addTrip(
                new Trip(new Driver("Mario"), Set.of(new Passenger("Laura")), 10, 2.5, null)
        );
        Trip expectedTrip = new Trip(new Driver("Mario"), Set.of(new Passenger("Laura")), 10, 2.5, null);
        boolean tripPersisted = taxiParkDb.getAllTrips().stream()
                .anyMatch(expectedTrip::equals);
        assertTrue(tripPersisted);
    }

    @BeforeTest
    public void setup() throws SQLException, ClassNotFoundException {
        createTestDatabase();

        DataSource dataSource = getDataSource();

        TripRepository tripRepository = new TripRepositoryImpl(dataSource);
        PassengerRepository passengerRepository = new PassengerRepositoryImpl(dataSource);
        DriverRepository driverRepository = new DriverRepositoryImpl(dataSource);

        TripService tripService = new TripServiceImpl(tripRepository);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        PassengerService passengerService = new PassengerServiceImpl(passengerRepository);

        taxiParkDb = new RelationalTaxiParkDb(driverService, passengerService, tripService);
    }

    @AfterTest
    public void tearDown() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                DEFAULT_DATABASE_URL, DEFAULT_USER, DEFAULT_PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(PREVENT_NEW_CONNECTIONS);
            statement.execute(CLOSE_CURRENT_CONNECTIONS);
            statement.executeUpdate(DROP_DATABASE);
        }
    }

    private void createTestDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        try (Connection connection = DriverManager.getConnection(DEFAULT_DATABASE_URL,
                DEFAULT_USER, DEFAULT_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_DATABASE);
        }
        DataSource dataSource = getDataSource();

        Flyway flyway = Flyway
                .configure()
                .locations(TEST_DATABASE_DDL_LOCATION)
                .dataSource(dataSource).load();
        flyway.migrate();
    }

    private static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DRIVER);
        config.setJdbcUrl(TEST_DATABASE_URL);
        config.setUsername(DEFAULT_USER);
        config.setPassword(DEFAULT_PASSWORD);
        return new HikariDataSource(config);
    }


}

