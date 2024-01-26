package pl.wielkopolan.flightscraper.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.wielkopolan.flightscraper.data.Flight;

import java.util.Optional;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {
    Optional<Flight> findByPackageId(String packageId);
}
