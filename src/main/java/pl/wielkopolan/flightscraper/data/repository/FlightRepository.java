package pl.wielkopolan.flightscraper.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wielkopolan.flightscraper.data.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends MongoRepository<Flight, String> {
    Optional<Flight> findByPackageId(String packageId);
    List<Flight> findByPackageIdIn(List<String> packageIdList);
}
