package pl.wielkopolan.flightpersistence.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.wielkopolan.flightpersistence.data.Flight;

public final class JsonToFlightMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Flight mapJsonToFlight(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, Flight.class);
    }
    private JsonToFlightMapper() {
        //Util class - private constructor
    }
}