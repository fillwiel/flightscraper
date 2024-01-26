package pl.wielkopolan.flightscraper.data.rainbow;

import java.util.List;

public record TicketDto(int id, FlightInfoDto departureInfo, FlightInfoDto arrivalInfo, int price, List<String> packages) {
}
