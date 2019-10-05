package com.pitchbook.bootcamp.io.analytics;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Trip;
import com.pitchbook.bootcamp.io.source.db.TaxiParkDb;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TaxiParkDbAnalytic implements TaxiParkAnalytic {

    private TaxiParkDb taxiPark;

    public TaxiParkDbAnalytic(TaxiParkDb taxiPark) {
        this.taxiPark = taxiPark;
    }

    @Override
    public Map<Driver, Integer> calculateDriversEarnings() {
        Map<Driver, Integer> resultMap = new HashMap<>();
        Map<Driver, Double> map = taxiPark.getAllTrips().stream().collect(Collectors.toMap(Trip::getDriver,
                Trip::getCost, Double::sum));
        map.forEach((key, value) -> resultMap.put(key, value.intValue()));

        return resultMap;
    }

}
