package pl.wielkopolan.flightpublisher.data.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wielkopolan.flightpublisher.data.Flight;

import java.util.List;

public interface FlightRepository extends MongoRepository<Flight, String> {
    List<Flight> findByPackageIdIn(List<String> packageIdList);
}
