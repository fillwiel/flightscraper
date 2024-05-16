package pl.wielkopolan.flightpublisher.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.Flight;
import pl.wielkopolan.flightpublisher.data.repository.FlightRepository;
import pl.wielkopolan.flightpublisher.services.RepositoryService;

import java.util.Collections;
import java.util.List;

@Service
public class FlightRepositoryService implements RepositoryService {
    private static final Logger log = LoggerFactory.getLogger(FlightRepositoryService.class);
    private final FlightRepository flightRepository;

    @Override
    public List<Flight> findFlightsByPromotionPackages(List<String> promotionPackages) {
        try {
            return flightRepository.findByPackageIdIn(promotionPackages);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error looking up flights in repository.", e);
        }
        return Collections.emptyList();
    }

    public FlightRepositoryService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
}
