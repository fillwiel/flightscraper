package pl.wielkopolan.flightpublisher.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.config.KafkaConfigProps;
import pl.wielkopolan.flightpublisher.data.Flight;
import pl.wielkopolan.flightpublisher.services.FlightPublisherService;
import pl.wielkopolan.flightpublisher.exceptions.FlightPublishException;

/**
 * Publishes Flights to a Kafka topic.
 */
@Service
@Slf4j
public class KafkaFlightPublisherService implements FlightPublisherService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;

    public KafkaFlightPublisherService(
            final ObjectMapper objectMapper,
            final KafkaTemplate<String, String> kafkaTemplate,
            final KafkaConfigProps kafkaConfigProps) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfigProps = kafkaConfigProps;
    }

    @Override
    public void publish(final Flight flight) {
        try {
            final String payload = objectMapper.writeValueAsString(flight);
            log.debug("Publishing flight: {}", payload);
            kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
        } catch (final JsonProcessingException ex) {
            throw new FlightPublishException("Unable to publish flight", ex, flight);
        }
    }

}
