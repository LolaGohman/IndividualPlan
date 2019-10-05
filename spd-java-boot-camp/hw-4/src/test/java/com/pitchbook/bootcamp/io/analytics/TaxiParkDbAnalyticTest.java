package com.pitchbook.bootcamp.io.analytics;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.source.db.FileBackedTaxiParkDb;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
import com.pitchbook.bootcamp.io.source.initialization.TripsPopulator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TaxiParkDbAnalyticTest {

    private TaxiParkAnalytic taxiParkAnalytic;
    private TaxiParkDb taxiParkDb;

    @BeforeMethod
    public void setUp() {
        String tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "io-test-" + System.currentTimeMillis()).toAbsolutePath().toString();
        taxiParkDb = new FileBackedTaxiParkDb(Paths.get(tempDir, "taxiPark.db").toAbsolutePath().toString());
        taxiParkAnalytic = new TaxiParkDbAnalytic(taxiParkDb);
    }

    @Test
    public void shouldSaveAllDriversEarningsIntoMap() {
        Path tripsFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/trips.dat").toAbsolutePath();
        TripsPopulator populator = new TripsPopulator(tripsFile.toString());

        populator.apply(taxiParkDb);
        populator.apply(taxiParkDb);
        Map<Driver, Integer> expectedResult = Map.of(new Driver("Mario"), 155,
                new Driver("Uberto"),-6558 );

        assertEquals(taxiParkAnalytic.calculateDriversEarnings(), expectedResult);
    }

    @Test
    public void shouldReturnEmptyMapWhenNoTripsPresentInDatabase(){
        Path tripsFile = Paths.get("src/test/java/com/pitchbook/bootcamp/io/data/empty.file.dat").toAbsolutePath();
        TripsPopulator populator = new TripsPopulator(tripsFile.toString());

        populator.apply(taxiParkDb);

        assertTrue(taxiParkAnalytic.calculateDriversEarnings().isEmpty());
    }
}
