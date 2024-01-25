package pl.wielkopolan.flightscraper.data;

import java.util.List;

public record TicketDto(int id, FlightDto outboundFlight, FlightDto inboundFlight, int price, List<String> packages) {
}
