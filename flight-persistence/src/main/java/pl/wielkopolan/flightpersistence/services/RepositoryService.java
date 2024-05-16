package pl.wielkopolan.flightpersistence.services;

import pl.wielkopolan.flightpersistence.data.Flight;

import java.util.List;

public interface RepositoryService {
    Flight save(Flight flight);

    void saveFlights(List<Flight> flights);
}
