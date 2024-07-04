package pl.wielkopolan.flightpersistence.services;

import pl.wielkopolan.flightpersistence.domain.Flight;
import pl.wielkopolan.flightpersistence.domain.PriceHistory;

public interface PriceHistoryService {
    boolean updateFlightPriceHistoryIfChanged(Flight existingFlight);

    PriceHistory createPriceHistoryItem(int price);
}
