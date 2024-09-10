package pl.wielkopolan.subscriptionpersistence.data.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public record Subscription(@Id String id, String userId, @NotBlank(message = "Device token is mandatory") String deviceToken, @NotBlank(message = "Arrival city is mandatory") String arrivalCity, String departureCity, @NotNull(message = "Price threshold is mandatory") int priceThreshold) {
    public Subscription(String userId, @NotBlank(message = "Device token is mandatory") String deviceToken, @NotBlank(message = "Arrival city is mandatory") String arrivalCity, String departureCity, @NotNull(message = "Price threshold is mandatory") int priceThreshold) {
        this(generateId(userId, deviceToken, arrivalCity, departureCity, priceThreshold), userId, deviceToken, arrivalCity, departureCity, priceThreshold);
    }
    private static String generateId(String userId, String deviceToken, String arrivalCity, String departureCity, int priceThreshold) {
        String uniqueString = userId + deviceToken + arrivalCity + departureCity + priceThreshold;
        return Integer.toHexString(uniqueString.hashCode());
    }
}