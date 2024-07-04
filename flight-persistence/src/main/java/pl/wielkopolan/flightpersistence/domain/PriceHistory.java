package pl.wielkopolan.flightpersistence.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public record PriceHistory(int price, Date dateOfChange) {
}
