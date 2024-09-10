package pl.wielkopolan.notification.data.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Subscription(String id, String userId, String deviceToken, String arrivalCity, String departureCity,
                           int priceThreshold) {
}
