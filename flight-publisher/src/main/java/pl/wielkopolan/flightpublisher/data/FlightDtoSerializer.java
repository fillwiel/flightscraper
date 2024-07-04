package pl.wielkopolan.flightpublisher.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;
import pl.wielkopolan.flightpublisher.exception.FlightDtoSerializationException;

public class FlightDtoSerializer implements Serializer<FlightDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, FlightDto data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new FlightDtoSerializationException("Failed to serialize FlightDto", e);
        }
    }
}