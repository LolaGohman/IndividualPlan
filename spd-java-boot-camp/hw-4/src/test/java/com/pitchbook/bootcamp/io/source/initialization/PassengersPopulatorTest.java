package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.source.db.FileBackedTaxiParkDb;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class PassengersPopulatorTest {
    private TaxiParkDb taxiParkDb;


    @BeforeMethod
    public void setUp() {
        String tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "io-test-" + System.currentTimeMillis()).toAbsolutePath().toString();
        taxiParkDb = new FileBackedTaxiParkDb(Paths.get(tempDir, "taxiPark.db").toAbsolutePath().toString());
    }


    @Test
    public void shouldReturnSetOfPassengersIfExists() {
        Path dataFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/" +
                "passengers.dat").toAbsolutePath();
        DbInitializer dbInitializer = new PassengersPopulator((dataFile.toString()));

        dbInitializer.apply(taxiParkDb);
        Set<Passenger> expectedResult = new HashSet<>(Arrays.asList(new Passenger("Antonio"),
                new Passenger("Carla"),
                new Passenger("Mario")));

        assertCollectionEqualsIgnoringOrder(taxiParkDb.getAllPassengers(), expectedResult);
    }

    @Test
    public void shouldReturnEmptySetWhenPassengersNotFound() {
        Path dataFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/" +
                "empty.file.dat").toAbsolutePath();
        DbInitializer dbInitializer = new PassengersPopulator(dataFile.toString());

        dbInitializer.apply(taxiParkDb);

        assertTrue(taxiParkDb.getAllPassengers().isEmpty());
    }

    private static <E> void assertCollectionEqualsIgnoringOrder(Collection<E> first,
                                                                Collection<E> second) {
        assertTrue(first.size() == second.size() &&
                first.containsAll(second) && second.containsAll(first));
    }
}
