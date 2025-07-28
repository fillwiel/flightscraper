package pl.wielkopolan.subscriptionpersistence.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.wielkopolan.subscriptionpersistence.data.domain.Subscription;
import pl.wielkopolan.subscriptionpersistence.data.dto.SubscriptionDto;
import pl.wielkopolan.subscriptionpersistence.services.RepositoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api")
public class SubscriptionController {
    private final String apiKey;
    private final RepositoryService repositoryService;

    public SubscriptionController(@Value(value = "${rest.api.key}") String apiKey, RepositoryService repositoryService) {
        this.apiKey = apiKey;
        this.repositoryService = repositoryService;
    }

    @GetMapping("/subscriptions")
    public List<SubscriptionDto> getSubscriptions(@RequestParam String deviceToken, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        return repositoryService.getByDeviceToken(deviceToken);
    }

    @DeleteMapping("/subscriptions")
    public ResponseEntity<String> deleteSubscriptionsByIds(@RequestParam(value = "ids") List<String> ids,
                                                           @RequestParam(value = "deviceToken") String deviceToken,
                                                           @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        try {
            repositoryService.deleteSubscriptionByIdInAndDeviceToken(ids, deviceToken);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Subscriptions deleted");
        } catch (Exception e) {
            log.error("Error deleting subscriptions for ids: {} and device token: {}", ids, deviceToken, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting subscriptions");
        }
    }

    @DeleteMapping("/subscriptions/device/{deviceToken}")
    public ResponseEntity<String> deleteAllDeviceSubscriptions(@PathVariable String deviceToken,
                                                               @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        try {
            repositoryService.deleteSubscriptionByDeviceToken(deviceToken);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Subscriptions deleted");
        } catch (Exception e) {
            log.error("Error deleting subscription for device token: {}", deviceToken, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting subscriptions");
        }
    }

    @PostMapping(value = "/subscriptions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createSubscription(@RequestBody @Valid SubscriptionDto subscriptionDto,
                                                     @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        log.debug("Received at create subscription {}", subscriptionDto);
        try {
            Subscription createdSubscription = repositoryService.createSubscription(subscriptionDto);
            log.debug("Subscription created: {}", createdSubscription);
            return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created");
        } catch (Exception e) {
            log.error("Error creating subscription", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating subscription");
        }
    }

    private void validateApiKey(String apiKey) {
        // Implement your API key validation logic here
        if (!this.apiKey.equals(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API Key");
        }
    }
}
