package com.pitchbook.bootcamp.io.analytics;

import com.pitchbook.bootcamp.io.model.Driver;
import java.util.Map;

public interface TaxiParkAnalytic {

    Map<Driver, Integer> calculateDriversEarnings();
}
