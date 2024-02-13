package pl.wielkopolan.flightscraper.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.repository.FlightRepository;
import pl.wielkopolan.flightscraper.services.RepositoryService;

import java.util.Collections;
import java.util.List;

@Service
public class FlightRepositoryService implements RepositoryService {
    private static final Logger log = LoggerFactory.getLogger(FlightRepositoryService.class);
    private final FlightRepository flightRepository;

    @Override
    public void saveFlights(final List<Flight> flights) {
        if (flights.isEmpty()) {
            return;
        }
        try {
            flightRepository.saveAll(flights);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error saving {} flights.", flights.size(), e);
        }
    }

    @Override
    public List<Flight> findFlightsInDatabase(List<String> promotionPackages) {
        try {
            return flightRepository.findByPackageIdIn(promotionPackages);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error looking up flights in repository.", e);
        }
        return Collections.emptyList();
    }

    @Autowired
    public FlightRepositoryService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
}
