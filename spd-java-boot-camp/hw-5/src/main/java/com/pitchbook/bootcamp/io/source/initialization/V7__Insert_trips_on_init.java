package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.repository.trip.TripRepository;
import com.pitchbook.bootcamp.io.source.db.repository.trip.TripRepositoryImpl;
import com.pitchbook.bootcamp.io.source.db.service.trip.TripService;
import com.pitchbook.bootcamp.io.source.db.service.trip.TripServiceImpl;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import javax.sql.DataSource;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class V7__Insert_trips_on_init extends BaseJavaMigration {

    private static final String TRIP_RESOURCE = "com/pitchbook/bootcamp/io/data/trips.dat";

    @Override
    public void migrate(Context context) throws Exception {
        String tripsPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource(TRIP_RESOURCE).toURI()).toString();
        FileDataPopulator fileDataPopulator = new FileDataPopulator(tripsPath);

        DataSource dataSource = context.getConfiguration().getDataSource();
        TripRepository repository = new TripRepositoryImpl(dataSource);
        TripService tripService = new TripServiceImpl(repository);

        List<Trip> trips = new ArrayList<>();
        for (Object object : fileDataPopulator.readCollectionFromFile()) {
            if (object instanceof Trip) {
                trips.add((Trip) object);
            }
        }
        tripService.addTrips(trips);
    }

}
