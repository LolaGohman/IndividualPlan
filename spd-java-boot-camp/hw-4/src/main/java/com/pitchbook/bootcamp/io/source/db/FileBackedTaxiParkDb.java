package com.pitchbook.bootcamp.io.source.db;

import com.pitchbook.bootcamp.AppendableOutputStream;
import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.Trip;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FileBackedTaxiParkDb implements TaxiParkDb {

    private String dbFilePath;

    public FileBackedTaxiParkDb(String dbFilePath) {
        File file = new File(dbFilePath);
        if (!file.exists()) {
            try {
                createNewFileWithSubFolders(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.dbFilePath = file.getAbsolutePath();
    }

    @Override
    public void addDriver(Driver driver) {
        if (findDriver(driver.getName()).isEmpty()) {

            writeObjectToDatabase(driver);
        }
    }

    @Override
    public Set<Driver> getAllDrivers() {
        return readAllDrivers();
    }

    @Override
    public Optional<Driver> findDriver(String name) {
        for (Driver driver : getAllDrivers()) {
            if (driver.getName().equals(name)) {
                return Optional.of(driver);
            }
        }
        return Optional.empty();
    }

    @Override
    public void addPassenger(Passenger passenger) {
        if (findPassenger(passenger.getName()).isEmpty()) {
            writeObjectToDatabase(passenger);
        }
    }

    @Override
    public Optional<Passenger> findPassenger(String name) {
        for (Passenger passenger : getAllPassengers()) {
            if (passenger.getName().equals(name)) {
                return Optional.of(passenger);
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<Passenger> getAllPassengers() {
        return readAllPassengers();
    }

    @Override
    public List<Trip> getAllTrips() {
        return readAllTrips();
    }

    @Override
    public void addTrip(Trip trip) {
        writeObjectToDatabase(trip);
    }









    private void createNewFileWithSubFolders(File file) throws IOException {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
            file.createNewFile();
        }
    }

    private <T> void writeObjectToDatabase(T object) {
        if (new File(dbFilePath).length() == 0) {
            try (FileOutputStream fos = new FileOutputStream(dbFilePath, true);
                 ObjectOutputStream aos = new ObjectOutputStream(fos)) {
                aos.writeObject(object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileOutputStream fos = new FileOutputStream(dbFilePath, true);
                 AppendableOutputStream aos = new AppendableOutputStream(fos)) {
                aos.writeObject(object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<Passenger> readAllPassengers() {
        Set<Passenger> passengers = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(dbFilePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                Object object = ois.readObject();
                if (object instanceof Passenger) {
                    passengers.add((Passenger) object);
                }
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return passengers;
    }

    private Set<Driver> readAllDrivers() {
        Set<Driver> drivers = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(dbFilePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                Object object = ois.readObject();
                if (object instanceof Driver) {
                    drivers.add((Driver) object);
                }
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return drivers;
    }

    private List<Trip> readAllTrips() {
        List<Trip> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(dbFilePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                Object object = ois.readObject();
                if (object instanceof Trip) {
                    list.add((Trip) object);
                }
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}