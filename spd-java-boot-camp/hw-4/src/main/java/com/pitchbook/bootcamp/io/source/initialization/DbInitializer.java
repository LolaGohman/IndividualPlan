package com.pitchbook.bootcamp.io.source.initialization;

import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;

public interface DbInitializer {

    void apply(TaxiParkDb taxiParkDb);

}
