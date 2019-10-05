package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.FileBackedTaxiParkDb;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.testng.Assert.assertTrue;

public class TripsPopulatorTest {

    private TaxiParkDb taxiParkDb;


    @BeforeMethod
    public void setUp() {
        String tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "io-test-" + System.currentTimeMillis()).toAbsolutePath().toString();
        taxiParkDb = new FileBackedTaxiParkDb(Paths.get(tempDir, "taxiPark.db").toAbsolutePath().toString());
    }


    @Test
    public void shouldReturnListOfAllTripsIfExists() {
        Path dataFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/" +
                "trips.dat").toAbsolutePath();
        DbInitializer dbInitializer = new TripsPopulator((dataFile.toString()));

        dbInitializer.apply(taxiParkDb);
        Trip trip1 = new Trip(new Driver("Mario"),
                new HashSet<>(Arrays.asList(new Passenger("Adriano"), new Passenger("Carla"))),
                33, 45, 3.9872443153697016E-6);
        Trip trip2 = new Trip(new Driver("Uberto"),
                new HashSet<>(Arrays.asList(new Passenger("Hugo"), new Passenger("Antonio"))),
                78, 645, 5.535815085939095);
        List<Trip> expectedResult = Arrays.asList(trip1, trip2);

        assertCollectionEqualsIgnoringOrder(taxiParkDb.getAllTrips(), expectedResult);
    }

    @Test
    public void shouldReturnEmptyListWhenTripsNotFound() {
        Path dataFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/" +
                "empty.file.dat").toAbsolutePath();
        DbInitializer dbInitializer = new TripsPopulator(dataFile.toString());

        dbInitializer.apply(taxiParkDb);

        assertTrue(taxiParkDb.getAllTrips().isEmpty());
    }

    private static <E> void assertCollectionEqualsIgnoringOrder(Collection<E> first,
                                                                Collection<E> second) {
        assertTrue(first.size() == second.size() &&
                first.containsAll(second) && second.containsAll(first));
    }
}
