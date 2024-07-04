package pl.wielkopolan.flightpublisher.data.rainbow;

import java.util.Date;

public record FlightDto(String packageId, String airport, String country, String arrivalCity, String departureCity, Date date, int currentPrice) {
}
