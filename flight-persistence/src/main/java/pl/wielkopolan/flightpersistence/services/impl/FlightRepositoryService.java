package pl.wielkopolan.flightpersistence.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpersistence.data.Flight;
import pl.wielkopolan.flightpersistence.data.repository.FlightRepository;
import pl.wielkopolan.flightpersistence.services.RepositoryService;

import java.util.List;

@Service
public class FlightRepositoryService implements RepositoryService {
    private static final Logger log = LoggerFactory.getLogger(FlightRepositoryService.class);
    private final FlightRepository flightRepository;

    @Override
    public Flight save(final Flight flight) {
        try {
            return flightRepository.save(flight);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error saving flight {}", flight, e);
        }
        return flight;
    }

    @Override
    public void saveFlights(final List<Flight> flights) {
        try {
            flightRepository.saveAll(flights);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error saving {} flights.", flights.size(), e);
        }
    }

    public FlightRepositoryService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
}
