package pl.wielkopolan.notification.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.wielkopolan.notification.data.FlightDto;
import pl.wielkopolan.notification.services.FlightSubscriptionProcessingService;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlightPublishedListener {
    private final ObjectMapper objectMapper;
    private final FlightSubscriptionProcessingService flightSubscriptionProcessingService;

    @KafkaListener(topics = "flights.published")
    public String listens(final String in) {
        log.debug("Received Flight: {}", in);
        try {
            final FlightDto flightFromTopic = objectMapper.readValue(in, FlightDto.class);
            flightSubscriptionProcessingService.notifyMatchingSubscribers(flightFromTopic);
        } catch (final JsonProcessingException ex) {
            log.error("Cannot map message to Flight: {}", in);
        }
        return in;
    }
}
