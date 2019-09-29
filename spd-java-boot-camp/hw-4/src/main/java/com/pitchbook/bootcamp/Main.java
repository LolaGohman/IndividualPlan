package com.pitchbook.bootcamp;

import com.pitchbook.bootcamp.io.analytics.TaxiParkDbAnalytic;
import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.source.db.FileBackedTaxiParkDb;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
import com.pitchbook.bootcamp.io.source.initialization.DriversPopulator;
import com.pitchbook.bootcamp.io.source.initialization.PassengersPopulator;
import com.pitchbook.bootcamp.io.source.initialization.TripsPopulator;
import com.pitchbook.bootcamp.util.NotImplementedError;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Main {

    public static void main(String[] args) {

        String dbFilePath = Thread.currentThread().getContextClassLoader().getResource("com/pitchbook/bootcamp/io/db/db.json").getPath();
        String passengersPath = Thread.currentThread().getContextClassLoader().getResource("com/pitchbook/bootcamp/io/data/passengers.dat").getPath();
        String driversPath = Thread.currentThread().getContextClassLoader().getResource("com/pitchbook/bootcamp/io/data/drivers.dat").getPath();
        String tripsPath = Thread.currentThread().getContextClassLoader().getResource("com/pitchbook/bootcamp/io/data/trips.dat").getPath();

        TaxiParkDb taxiParkDb = new FileBackedTaxiParkDb(dbFilePath);

        List.of(
                new PassengersPopulator(passengersPath),
                new DriversPopulator(driversPath),
                new TripsPopulator(tripsPath)
        ).forEach(dbPopulator -> dbPopulator.apply(taxiParkDb));

        TaxiParkDb reconnectedTaxiParkDb = new FileBackedTaxiParkDb(dbFilePath);

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
