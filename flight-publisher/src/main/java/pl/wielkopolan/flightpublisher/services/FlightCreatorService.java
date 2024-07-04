package pl.wielkopolan.flightpublisher.services;

import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;

import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

public interface FlightCreatorService {
    Stream<FlightDto> createFlights(Map<TicketDto, Date> ticketDateOfFlightMap);
}
