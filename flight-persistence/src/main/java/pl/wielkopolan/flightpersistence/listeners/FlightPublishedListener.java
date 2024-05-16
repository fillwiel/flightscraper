package pl.wielkopolan.flightpersistence.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.wielkopolan.flightpersistence.data.Flight;
import pl.wielkopolan.flightpersistence.services.RepositoryService;
import pl.wielkopolan.flightpersistence.util.JsonToFlightMapper;

@Profile("dev")
@Slf4j
@Component
public class FlightPublishedListener {
    private final RepositoryService repositoryService;

    public FlightPublishedListener(
            final RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @KafkaListener(topics = "flights.published")
    public String listens(final String in) {
        log.debug("Received Flight: {}", in);
        try {
            final Flight flightFromTopic = JsonToFlightMapper.mapJsonToFlight(in);
            Flight savedFlight = repositoryService.save(flightFromTopic);
            log.debug("Flight persisted: {}", savedFlight);
        } catch (final JsonProcessingException ex) {
            log.error("Cannot map message to Flight: {}", in);
        }
        return in;
    }
}
