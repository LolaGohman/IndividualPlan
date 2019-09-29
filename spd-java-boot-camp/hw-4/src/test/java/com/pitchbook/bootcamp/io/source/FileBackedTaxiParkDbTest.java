package com.pitchbook.bootcamp.io.source;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.FileBackedTaxiParkDb;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class FileBackedTaxiParkDbTest {

    private File tempFolder;

    @BeforeMethod
    public void setUp() {
        tempFolder = new File(System.getProperty("java.io.tmpdir"), "io-test-" + System.currentTimeMillis());
    }

    @Test
    public void database_file_should_be_created_if_does_not_exist() {
        new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());

        boolean created = new File(tempFolder, "taxiPark.db").exists();
        assertTrue(created);
    }

    @Test
    public void database_should_be_initialized_as_empty_after_creation() {
        TaxiParkDb taxiParkDb = new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());

        assertNotNull(taxiParkDb);
        assertEmpty(taxiParkDb.getAllPassengers());
        assertEmpty(taxiParkDb.getAllTrips());
    }

    @Test
    public void driver_insertion_should_be_persistent() {
        TaxiParkDb taxiParkDb = new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());

        taxiParkDb.addDriver(new Driver("Mario"));

        TaxiParkDb reinitializedTaxiParkDb = new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());
        boolean marioPersisted = reinitializedTaxiParkDb.findDriver("Mario").isPresent();
        assertTrue(marioPersisted);
    }

    @Test
    public void passenger_insertion_should_be_persistent() {
        TaxiParkDb taxiParkDb = new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());

        taxiParkDb.addPassenger(new Passenger("Laura"));

        TaxiParkDb reinitializedTaxiParkDb = new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());
        boolean lauraPersisted = reinitializedTaxiParkDb.findPassenger("Laura").isPresent();
        assertTrue(lauraPersisted);
    }

    @Test
    public void trip_insertion_should_be_persistent() {
        TaxiParkDb taxiParkDb = new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());
        taxiParkDb.addDriver(new Driver("Mario"));
        taxiParkDb.addPassenger(new Passenger("Laura"));

        taxiParkDb.addTrip(
            new Trip(new Driver("Mario"), Set.of(new Passenger("Laura")), 10, 2.5, null)
        );

        TaxiParkDb reinitializedTaxiParkDb = new FileBackedTaxiParkDb(new File(tempFolder, "taxiPark.db").getAbsolutePath());
        Trip expectedTrip = new Trip(new Driver("Mario"), Set.of(new Passenger("Laura")), 10, 2.5, null);
        boolean tripPersisted = reinitializedTaxiParkDb.getAllTrips().stream()
            .anyMatch(expectedTrip::equals);
        assertTrue(tripPersisted);
    }

    private <T> void assertEmpty(Collection<T> items) {
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }
}
