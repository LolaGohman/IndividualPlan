package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.source.db.repository.passenger.PassengerRepository;
import com.pitchbook.bootcamp.io.source.db.repository.passenger.PassengerRepositoryImpl;
import com.pitchbook.bootcamp.io.source.db.service.passenger.PassengerService;
import com.pitchbook.bootcamp.io.source.db.service.passenger.PassengerServiceImpl;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import javax.sql.DataSource;
import java.nio.file.Paths;

public class V6__Insert_passengers_on_init extends BaseJavaMigration {

    private static final String PASSENGERS_RESOURCE = "com/pitchbook/bootcamp/io/data/passengers.dat";

    @Override
    public void migrate(Context context) throws Exception {
        String passengersPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource(PASSENGERS_RESOURCE).toURI()).toString();
        FileDataPopulator populator = new FileDataPopulator(passengersPath);

        DataSource dataSource = context.getConfiguration().getDataSource();
        PassengerRepository repository = new PassengerRepositoryImpl(dataSource);
        PassengerService service = new PassengerServiceImpl(repository);

        for (Object passenger : populator.readCollectionFromFile()) {
            service.addPassenger((Passenger) passenger);
        }
    }

}
