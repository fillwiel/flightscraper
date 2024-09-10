package pl.wielkopolan.subscriptionpersistence.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionDto {
    private String userId;
    @NotBlank(message = "Device token is mandatory to allow push notifications")
    private String deviceToken;
    @NotBlank(message = "Arrival city is mandatory")
    private String arrivalCity;
    private String departureCity;
    @NotNull(message = "Price threshold is mandatory")
    private int priceThreshold;
}