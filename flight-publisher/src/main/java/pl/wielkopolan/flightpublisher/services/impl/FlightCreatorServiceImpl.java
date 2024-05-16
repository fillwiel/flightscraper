package pl.wielkopolan.flightpublisher.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.PriceHistory;
import pl.wielkopolan.flightpublisher.services.FlightConnectionInfoService;
import pl.wielkopolan.flightpublisher.services.FlightCreatorService;
import pl.wielkopolan.flightpublisher.services.JsonConverterService;

import pl.wielkopolan.flightpublisher.data.Flight;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
public class FlightCreatorServiceImpl implements FlightCreatorService {
    private final JsonConverterService jsonConverterService;
    private final FlightConnectionInfoService flightConnectionInfoService;

    @Override
    public Stream<Flight> createNewFlights(Map<TicketDto, Date> ticketDateOfFlightMap) {
        return ticketDateOfFlightMap.entrySet().stream()
            .flatMap(ticket -> ticket.getKey().packages().stream()
                .map(packageId -> {
                    final JSONObject currentFlightInfo = flightConnectionInfoService.readFlightInfoFromWeb(packageId);
                    return jsonConverterService.createFlightFromJson(currentFlightInfo, packageId, ticket.getKey(), ticket.getValue());
                }));
    }

    @Override
    public Flight createUpdatedFlightsIfPriceChanged(final Flight flight) {
        return updateFlightIfPriceChanged(flight);
    }

    private Flight updateFlightIfPriceChanged(final Flight flight) {
        final JSONObject currentFlightInfo = flightConnectionInfoService.readFlightInfoFromWeb(flight.packageId());
        if (shouldUpdatePriceList(flight, currentFlightInfo)) {
            return jsonConverterService.updateFlightPriceHistory(flight, currentFlightInfo);
        }
        return flight;
    }

    /**
     * Checks if a priceHistory should be appended because
     * change has occurred for a given flight,
     * or if the flight priceHistory is empty.
     * @param flight flight from repository
     * @param currentFlightInfo current info from web
     * @return TRUE if priceHistory should be appended
     */
    private boolean shouldUpdatePriceList(Flight flight, JSONObject currentFlightInfo) {
    PriceHistory currentPrice = jsonConverterService.createPriceHistoryItem(currentFlightInfo);
    Optional<PriceHistory> mostRecentPriceRecorded = flight.priceHistory().stream()
            .max(Comparator.comparing(PriceHistory::dateOfChange));
    if (mostRecentPriceRecorded.isPresent()) {
        int recordedPrice = mostRecentPriceRecorded.get().price();
        if (recordedPrice != currentPrice.price()) {
            log.debug("Flight {} to {} price changed. Most recent price recorded: {}, current price: {}",
                    flight.packageId(), flight.arrivalCity(), recordedPrice, currentPrice.price());
            return true;
        }
        return false;
    }
    return true;
}

    public FlightCreatorServiceImpl(JsonConverterService jsonConverterService,
                                    FlightConnectionInfoService flightConnectionInfoService) {
        this.jsonConverterService = jsonConverterService;
        this.flightConnectionInfoService = flightConnectionInfoService;
    }
}
