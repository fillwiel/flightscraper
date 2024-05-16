package pl.wielkopolan.flightpersistence.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wielkopolan.flightpersistence.data.Flight;

public interface FlightRepository extends MongoRepository<Flight, String> {
}
