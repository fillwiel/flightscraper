package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.rainbow.TicketDto;
import pl.wielkopolan.flightscraper.services.FlightConnectionInfoService;
import pl.wielkopolan.flightscraper.services.FlightCreatorService;
import pl.wielkopolan.flightscraper.services.JsonConverterService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FlightCreatorServiceImpl implements FlightCreatorService {
    private final JsonConverterService jsonConverterService;
    private final FlightConnectionInfoService flightConnectionInfoService;

    @Override
    public List<Flight> createNewFlights(Map<TicketDto, Date> ticketDateOfFlightMap) {
        List<Flight> newFlights = new ArrayList<>();
        for (Map.Entry<TicketDto, Date> ticket : ticketDateOfFlightMap.entrySet()) {
            for (String packageId : ticket.getKey().packages()) {
                final JSONObject currentFlightInfo = flightConnectionInfoService.readFlightInfoFromWeb(packageId);
                Flight flightFromJson =
                        jsonConverterService.createFlightFromJson(currentFlightInfo, packageId, ticket.getKey(),
                                ticket.getValue());
                newFlights.add(flightFromJson);
            }
        }
        return newFlights;
    }

    @Override
    public List<Flight> createUpdatedFlightsIfPriceChanged(List<Flight> flights) {
        List<Flight> flightsToBeUpdated = new ArrayList<>();
        flights.forEach(flight -> {
            final JSONObject currentFlightInfo = flightConnectionInfoService.readFlightInfoFromWeb(flight.packageId());
            if (jsonConverterService.isPriceChanged(flight, currentFlightInfo)) {
                flightsToBeUpdated.add(jsonConverterService.updateFlightPriceHistory(flight, currentFlightInfo));
            }
        });
        return flightsToBeUpdated;
    }

    @Autowired
    public FlightCreatorServiceImpl(JsonConverterService jsonConverterService,
                                FlightConnectionInfoService flightConnectionInfoService) {
        this.jsonConverterService = jsonConverterService;
        this.flightConnectionInfoService = flightConnectionInfoService;
    }
}
