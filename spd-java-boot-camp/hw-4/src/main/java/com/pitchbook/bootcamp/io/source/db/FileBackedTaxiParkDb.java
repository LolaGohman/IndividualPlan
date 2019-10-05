package com.pitchbook.bootcamp.io.source.db;

import com.pitchbook.bootcamp.io.model.Driver;
import com.pitchbook.bootcamp.io.model.Passenger;
import com.pitchbook.bootcamp.io.model.TaxiPark;
import com.pitchbook.bootcamp.io.model.Trip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FileBackedTaxiParkDb implements TaxiParkDb {

    private final String dbFilePath;
    private final TaxiPark taxiParkEmulator = new TaxiPark(new HashSet<>(),
            new HashSet<>(), new ArrayList<>());

    public FileBackedTaxiParkDb(String dbFilePath) {
        Path file = Paths.get(dbFilePath);
        if (!Files.exists(file)) {
            try {
                createNewFileWithSubFolders(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.dbFilePath = file.toAbsolutePath().toString();
        fillTaxiParkEmulatorOnInit();
    }

    @Override
    public void addDriver(Driver driver) {
        if (findDriver(driver.getName()).isEmpty()) {
            writeObjectToDatabase(driver);
            taxiParkEmulator.getAllDrivers().add(driver);
        }
    }

    @Override
    public Set<Driver> getAllDrivers() {
        return taxiParkEmulator.getAllDrivers();
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
            taxiParkEmulator.getAllPassengers().add(passenger);
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
        return taxiParkEmulator.getAllPassengers();
    }

    @Override
    public List<Trip> getAllTrips() {
        return taxiParkEmulator.getAllTrips();
    }

    @Override
    public void addTrip(Trip trip) {
        writeObjectToDatabase(trip);
        taxiParkEmulator.getAllTrips().add(trip);
    }


    private void createNewFileWithSubFolders(Path file) throws IOException {
        Path parent = file.getParent();
        if (!Files.exists(parent)) {
            Files.createDirectories(parent);
        }
        Files.createFile(file);
    }

    private void fillTaxiParkEmulatorOnInit() {
        taxiParkEmulator.getAllDrivers().addAll(new HashSet<>(readObjectsFromDatabase(Driver.class)));
        taxiParkEmulator.getAllPassengers().addAll(new HashSet<>(readObjectsFromDatabase(Passenger.class)));
        taxiParkEmulator.getAllTrips().addAll(readObjectsFromDatabase(Trip.class));
    }

    private <T> List<T> readObjectsFromDatabase(Class<T> objectClass) {
        List<T> list = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(Paths.get(dbFilePath));
             BufferedInputStream bis = new BufferedInputStream(inputStream);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            while (true) {
                Object object = ois.readObject();
                if (object.getClass() == objectClass) {
                    list.add((T) object);
                }
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private <T> void writeObjectToDatabase(T object) {
        try (OutputStream os = Files.newOutputStream(Paths.get(dbFilePath), StandardOpenOption.APPEND);
             BufferedOutputStream bos = new BufferedOutputStream(os);
             ObjectOutputStream aos = getObjectOutputStream(bos)) {
            aos.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectOutputStream getObjectOutputStream(OutputStream out) throws IOException {
        return isFileEmpty(Paths.get(dbFilePath)) ?
                new ObjectOutputStream(out) : new AppendableOutputStream(out);
    }

    private boolean isFileEmpty(Path file) throws IOException {
        return Files.size(file) == 0;
    }

}