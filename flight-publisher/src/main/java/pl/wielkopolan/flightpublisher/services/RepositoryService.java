package pl.wielkopolan.flightpublisher.services;


import pl.wielkopolan.flightpublisher.data.Flight;

import java.util.List;

public interface RepositoryService {

    List<Flight> findFlightsByPromotionPackages(List<String> promotionPackages);
}
