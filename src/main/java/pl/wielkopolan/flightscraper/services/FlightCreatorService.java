package pl.wielkopolan.flightscraper.services;

import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.rainbow.TicketDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FlightCreatorService {
    List<Flight> createNewFlights(Map<TicketDto, Date> ticketDateOfFlightMap);

    List<Flight> createUpdatedFlightsIfPriceChanged(List<Flight> flights);
}
