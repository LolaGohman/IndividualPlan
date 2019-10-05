package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Driver;
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

public class DriversPopulatorTest {

    private TaxiParkDb taxiParkDb;

    @BeforeMethod
    public void setUp() {
        String tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "io-test-" + System.currentTimeMillis()).toAbsolutePath().toString();
        taxiParkDb = new FileBackedTaxiParkDb(Paths.get(tempDir, "taxiPark.db").toAbsolutePath().toString());
    }


    @Test
    public void shouldReturnSetOfAllDriversIfExists() {
        Path dataFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/" +
                "drivers.dat").toAbsolutePath();
        DbInitializer dbInitializer = new DriversPopulator(dataFile.toString());

        dbInitializer.apply(taxiParkDb);
        Set<Driver> expectedResult = new HashSet<>(Arrays.asList(new Driver("Uberto"),
                new Driver("Mario"), new Driver("Gonzalo"), new Driver("Giovani")));

        assertCollectionEqualsIgnoringOrder(taxiParkDb.getAllDrivers(), expectedResult);
    }

    @Test
    public void shouldReturnEmptySetWhenDriversNotFound() {
        Path dataFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/" +
                "empty.file.dat").toAbsolutePath();
        DbInitializer dbInitializer = new DriversPopulator(dataFile.toString());

        dbInitializer.apply(taxiParkDb);

        assertTrue(taxiParkDb.getAllDrivers().isEmpty());
    }

    private static <E> void assertCollectionEqualsIgnoringOrder(Collection<E> first,
                                                                Collection<E> second) {
        assertTrue(first.size() == second.size() &&
                first.containsAll(second) && second.containsAll(first));
    }

}
