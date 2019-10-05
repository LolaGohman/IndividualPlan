package com.pitchbook.bootcamp.io.source.db.service.trip;

import com.pitchbook.bootcamp.io.model.Trip;
import java.util.List;

public interface TripService {

    void addTrips(List<Trip> trip);

    List<Trip> getAllTrips();

}
