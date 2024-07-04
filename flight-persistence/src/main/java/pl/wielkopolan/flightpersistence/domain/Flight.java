package pl.wielkopolan.flightpersistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public record Flight(@Id String packageId, String airport, String country, String arrivalCity, String departureCity, Date date, List<PriceHistory> priceHistory) {
}
