package pl.wielkopolan.flightpersistence.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.wielkopolan.flightpersistence.data.FlightDto;
import pl.wielkopolan.flightpersistence.services.FlightProcessingService;

@Profile("dev")
@Slf4j
@Component
@RequiredArgsConstructor
public class FlightPublishedListener {
    private final FlightProcessingService flightProcessingService;

    @KafkaListener(topics = "flights.published", containerFactory = "flightKafkaListenerContainerFactory")
    public void flightDtoListener(final FlightDto flight) {
        log.debug("Received Flight: {}", flight.packageId());
        flightProcessingService.processFlight(flight);
    }
}
