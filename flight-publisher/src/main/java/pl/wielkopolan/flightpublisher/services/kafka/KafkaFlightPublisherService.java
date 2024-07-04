package pl.wielkopolan.flightpublisher.services.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;
import pl.wielkopolan.flightpublisher.services.PublisherService;

/**
 * Publishes Flights to a Kafka topic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaFlightPublisherService implements PublisherService<FlightDto> {

    private final ProducerService<FlightDto> flightProducerService;

    @Override
    public void publish(final FlightDto flight) {
        flightProducerService.sendMessage(flight);
    }

}
