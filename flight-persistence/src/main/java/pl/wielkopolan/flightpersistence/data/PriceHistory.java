package pl.wielkopolan.flightpersistence.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public record PriceHistory(int returnFightId, int price, Date dateOfChange) {
}
