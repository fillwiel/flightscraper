package pl.wielkopolan.flightscraper.services;

import pl.wielkopolan.flightscraper.data.Flight;

import java.util.List;

public interface RepositoryService {
    void saveFlights(List<Flight> flights);

    List<Flight> findFlightsInDatabase(List<String> promotionPackages);
}
