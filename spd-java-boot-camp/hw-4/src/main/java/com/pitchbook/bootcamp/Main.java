package com.pitchbook.bootcamp;

import com.pitchbook.bootcamp.io.analytics.TaxiParkDbAnalytic;
import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.source.db.FileBackedTaxiParkDb;
import com.pitchbook.bootcamp.io.source.initialization.DriversPopulator;
import com.pitchbook.bootcamp.io.source.initialization.PassengersPopulator;
import com.pitchbook.bootcamp.io.source.initialization.TripsPopulator;
import com.pitchbook.bootcamp.util.NotImplementedError;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

        String dbFilePath = "hw-4/db/taxiPark.db";
        String passengersPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("com/pitchbook/bootcamp/io/data/passengers.dat").toURI()).toString();
        String driversPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("com/pitchbook/bootcamp/io/data/drivers.dat").toURI()).toString();
        String tripsPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("com/pitchbook/bootcamp/io/data/trips.dat").toURI()).toString();

        Files.deleteIfExists(Paths.get(dbFilePath).toAbsolutePath());
        FileBackedTaxiParkDb testTaxiParkDb = new FileBackedTaxiParkDb(Paths.get(dbFilePath).toAbsolutePath().toString());


        List.of(
                new PassengersPopulator(passengersPath),
                new DriversPopulator(driversPath),
                new TripsPopulator(tripsPath)
        ).forEach(dbPopulator -> dbPopulator.apply(testTaxiParkDb));

        FileBackedTaxiParkDb reconnectedTaxiParkDb = new FileBackedTaxiParkDb(dbFilePath);

        TaxiParkDbAnalytic analytics = new TaxiParkDbAnalytic(reconnectedTaxiParkDb);
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
