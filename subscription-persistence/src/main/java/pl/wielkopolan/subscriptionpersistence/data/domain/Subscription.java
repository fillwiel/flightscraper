package pl.wielkopolan.subscriptionpersistence.data.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

//TODO perhaps skip renaming entity?
@Document(collection = "subscriptions")
public record Subscription(String id, String userId, @NotBlank(message = "Device token is mandatory") String deviceToken, @NotBlank(message = "Arrival city is mandatory") String arrivalCity, String departureCity, @NotNull(message = "Price threshold is mandatory") int priceThreshold) {
    //TODO : hashcode may not be unique, consider using UUID.randomUUID().toString() (but then each save would need to find subscription by all fields)
    public Subscription(String userId, @NotBlank(message = "Device token is mandatory") String deviceToken, @NotBlank(message = "Arrival city is mandatory") String arrivalCity, String departureCity, @NotNull(message = "Price threshold is mandatory") int priceThreshold) {
        this(Integer.toHexString((userId + deviceToken + arrivalCity + departureCity + priceThreshold).hashCode()), userId, deviceToken, arrivalCity, departureCity, priceThreshold);
    }
}