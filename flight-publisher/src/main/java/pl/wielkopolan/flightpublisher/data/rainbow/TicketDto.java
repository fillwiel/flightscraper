package pl.wielkopolan.flightpublisher.data.rainbow;

import java.util.List;

public record TicketDto(int id, FlightInfoDto departureInfo, FlightInfoDto arrivalInfo, int price, List<String> packages) {
}
