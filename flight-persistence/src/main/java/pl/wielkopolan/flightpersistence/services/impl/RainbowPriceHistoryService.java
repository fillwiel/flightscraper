package pl.wielkopolan.flightpersistence.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpersistence.domain.Flight;
import pl.wielkopolan.flightpersistence.domain.PriceHistory;
import pl.wielkopolan.flightpersistence.services.FlightConnectionInfoService;
import pl.wielkopolan.flightpersistence.services.PriceHistoryService;
import pl.wielkopolan.flightpersistence.util.RainbowConstants;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RainbowPriceHistoryService implements PriceHistoryService {

    private final FlightConnectionInfoService flightConnectionInfoService;

    /**
     * Updates priceHistory for a given flight if the price has changed.
     *
     * @param flight flight from repository
     * @return TRUE if priceHistory was updated
     * @throws JSONException if JSON parsing fails for current data from web
     */
    @Override
    public boolean updateFlightPriceHistoryIfChanged(final Flight flight) throws JSONException {
        final JSONObject currentFlightInfo = flightConnectionInfoService.readFlightInfoFromWeb(flight.packageId());
        int currentPrice = getCurrentPrice(flight, currentFlightInfo);
        if (shouldUpdatePriceHistory(flight, currentPrice)) {
            flight.priceHistory().addLast(createPriceHistoryItem(currentPrice));
            return true;
        }
        return false;
    }

    private static int getCurrentPrice(Flight flight, JSONObject currentFlightInfo) {
        try {
           return (int) currentFlightInfo.get(RainbowConstants.PRICE.getValue());
        } catch (JSONException e) {
            log.error("Cannot read PRICE from JSON for packageId {}", flight.packageId(), e);
            throw e;
        }
    }

    /**
     * Checks if a priceHistory should be appended because
     * change has occurred for a given flight
     * or if the flight priceHistory is empty.
     *
     * @param existingFlight flight from repository
     * @param currentPrice   current price from web
     * @return TRUE if priceHistory should be appended
     */
    private boolean shouldUpdatePriceHistory(Flight existingFlight, int currentPrice) {
        Optional<PriceHistory> mostRecentPriceRecorded = existingFlight.priceHistory().stream()
                .max(Comparator.comparing(PriceHistory::dateOfChange));
        if (mostRecentPriceRecorded.isPresent()) {
            int recordedPrice = mostRecentPriceRecorded.get().price();
            if (recordedPrice != currentPrice) {
                log.debug("Flight {} to {} price changed. Most recent price recorded: {}, current price: {}",
                        existingFlight.packageId(), existingFlight.arrivalCity(), recordedPrice, currentPrice);
                return true;
            }
            log.debug("Flight {} price has not changed. No update needed.", existingFlight.packageId());
            return false;
        }
        log.error("Flight {} in repository is empty. Check publisher logs. Adding price anyway.", existingFlight.packageId());
        return true;
    }

    @Override
    public PriceHistory createPriceHistoryItem(int price) {
        return new PriceHistory(price, new Date());
    }
}
