package pl.wielkopolan.flightpersistence.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpersistence.data.repository.FlightRepository;
import pl.wielkopolan.flightpersistence.domain.Flight;
import pl.wielkopolan.flightpersistence.services.RepositoryService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightRepositoryService implements RepositoryService {
    private final FlightRepository flightRepository;

    @Override
    public Optional<Flight> findFlightByPackageId(final String packageId) {
        return flightRepository.findByPackageId(packageId);
    }

    @Override
    public Flight save(final Flight flight) {
        try {
            return flightRepository.save(flight);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error saving flight {}", flight, e);
            throw e;
        }
    }

    @Override
    public void saveFlights(final List<Flight> flights) {
        try {
            flightRepository.saveAll(flights);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error saving {} flights.", flights.size(), e);
            throw e;
        }
    }
}
