package com.pitchbook.bootcamp.collections;


import com.pitchbook.bootcamp.collections.taxipark.Driver;
import com.pitchbook.bootcamp.collections.taxipark.Passenger;
import com.pitchbook.bootcamp.collections.taxipark.TaxiPark;
import com.pitchbook.bootcamp.collections.taxipark.Trip;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.pitchbook.bootcamp.collections.TaxiParkTransformMethods.*;
import static com.pitchbook.bootcamp.collections.taxipark.TaxiParkTestUtilKt.*;
import static java.util.Map.entry;
import static org.testng.Assert.assertEquals;

public class TaxiParkTransformMethodsTest {

    @Test
    public void convert_passenger_name_to_upper_case() {
        List<String> collection = List.of("FlAsH", "BatMan", "SUPERMAN", "JokER", "aRROW");
        List<String> expected = List.of("FLASH", "BATMAN", "SUPERMAN", "JOKER", "ARROW");

        assertEquals(toUpperCase(collection), expected);
    }

    @Test
    public void convert_passenger_name_to_lover_case() {
        List<String> collection = List.of("FlAsH", "BatMan", "SUPERMAN", "JokER", "aRROW");
        List<String> expected = List.of("flash", "batman", "superman", "joker", "arrow");

        assertEquals(toLoverCase(collection), expected);
    }

    @Test
    public void leave_unique_passengers_in_collection() {
        List<Passenger> collection = List.of(
            new Passenger("Flash"),
            new Passenger("Flash"),
            new Passenger("Flash"),
            new Passenger("Flash"),
            new Passenger("Batman")
        );

        Set<Passenger> expected = Set.of(
            new Passenger("Flash"),
            new Passenger("Batman")
        );

        assertEquals(leaveUniqueElements(collection), expected);
    }

    @Test
    public void change_relation_between_driver_and_passenger() {
        Map<Driver, Passenger> map = Map.ofEntries(
            entry(driver(1), passenger(1)),
            entry(driver(2), passenger(2)),
            entry(driver(3), passenger(3)),
            entry(driver(4), passenger(4)),
            entry(driver(5), passenger(5))
        );

        Map<Passenger, Driver> expected = Map.ofEntries(
            entry(passenger(1), driver(1)),
            entry(passenger(2), driver(2)),
            entry(passenger(3), driver(3)),
            entry(passenger(4), driver(4)),
            entry(passenger(5), driver(5))
        );

        assertEquals(changeRelation(map), expected);
    }

    @Test
    public void find_passenger_trips() {
        Passenger passenger = passenger(1);

        Trip trip1 = trip(1, 1, 4, 2.3, null);
        Trip trip2 = trip(1, List.of(1, 2), 4, 2.3, null);
        Trip trip3 = trip(1, 3, 4, 2.3, null);

        Set<Trip> expected = Set.of(trip1, trip2);

        TaxiPark taxiPark = taxiPark(
            drivers(1, 2, 3),
            passengers(1, 2, 3),
            trip1, trip2, trip3
        );

        assertEquals(findPassengerTrips(taxiPark, passenger), expected);
    }

    @Test
    public void count_passenger_trips() {
        Passenger passenger1 = passenger(1);
        Passenger passenger2 = passenger(2);
        Passenger passenger3 = passenger(3);

        Trip trip1 = trip(1, 1);
        Trip trip2 = trip(1, List.of(1, 2));
        Trip trip3 = trip(1, 3);

        Map<Passenger,Integer> expected = Map.ofEntries(
            entry(passenger1, 2),
            entry(passenger2, 1),
            entry(passenger3, 1)
        );

        TaxiPark taxiPark = taxiPark(
            drivers(1, 2, 3),
            passengers(1, 2, 3),
            trip1, trip2, trip3
        );

        assertEquals(passengerToTripsCount(taxiPark), expected);
    }
}
