package com.pitchbook.bootcamp.io.source.db.service.trip;

import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.repository.trip.TripRepository;
import java.util.List;

public class TripServiceImpl implements TripService {

    private  final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public void addTrips(List<Trip> trip) {
            tripRepository.addTrips(trip);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.getAllTrips();
    }
}
