package com.pitchbook.bootcamp.io.source.db.repository.trip;

import com.pitchbook.bootcamp.io.model.Trip;

import java.util.List;

public interface TripRepository {

    void addTrips(List<Trip> trip);

    List<Trip> getAllTrips();

}
