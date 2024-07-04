package pl.wielkopolan.flightpersistence.services;

import pl.wielkopolan.flightpersistence.domain.Flight;

import java.util.List;
import java.util.Optional;

public interface RepositoryService {
    Optional<Flight> findFlightByPackageId(String packageId);

    Flight save(Flight flight);

    void saveFlights(List<Flight> flights);
}
