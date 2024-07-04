package pl.wielkopolan.flightpublisher.services.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;

@Service
public class FlightProducerService implements ProducerService<FlightDto> {
    private final String topic;
    private final KafkaTemplate<String, FlightDto> flightsKafkaTemplate;

    public FlightProducerService(@Value("${flightscraper.kafka.topic.flights}") String topic,
                                 KafkaTemplate<String, FlightDto> flightsKafkaTemplate) {
        this.topic = topic;
        this.flightsKafkaTemplate = flightsKafkaTemplate;
    }

    @Override
    public void sendMessage(FlightDto flight) {
        flightsKafkaTemplate.send(topic, flight);
    }
}
