package pl.wielkopolan.flightpublisher.exception;

/**
 * Custom exception for handling serialization problems with FlightDto.
 */
public class FlightDtoSerializationException extends RuntimeException {

    public FlightDtoSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}