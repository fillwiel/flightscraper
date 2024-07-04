package pl.wielkopolan.flightpersistence.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wielkopolan.flightpersistence.domain.Flight;

import java.util.Optional;

public interface FlightRepository extends MongoRepository<Flight, String> {
    Optional<Flight> findByPackageId(String packageId);
}
