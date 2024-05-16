package pl.wielkopolan.flightpublisher.services;

import pl.wielkopolan.flightpublisher.data.Flight;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;

import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

public interface FlightCreatorService {
    Stream<Flight> createNewFlights(Map<TicketDto, Date> ticketDateOfFlightMap);

    Flight createUpdatedFlightsIfPriceChanged(Flight flight);
}
