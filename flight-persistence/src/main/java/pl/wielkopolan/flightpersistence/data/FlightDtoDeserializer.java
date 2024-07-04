package pl.wielkopolan.flightpersistence.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import pl.wielkopolan.flightpersistence.exception.FlightDtoSerializationException;

public class FlightDtoDeserializer implements Deserializer<FlightDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public FlightDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, FlightDto.class);
        } catch (Exception e) {
            throw new FlightDtoSerializationException("Failed to deserialize FlightDto", e);
        }
    }
}