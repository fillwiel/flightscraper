package pl.wielkopolan.flightpublisher.exceptions;

import lombok.Getter;
import pl.wielkopolan.flightpublisher.data.Flight;

@Getter
public class FlightPublishException extends RuntimeException {

    private final transient Flight flight;

    public FlightPublishException(Flight flight) {
        this.flight = flight;
    }

    public FlightPublishException(String message, final Flight flight) {
        super(message);
        this.flight  = flight;
    }

    public FlightPublishException(Throwable cause, final Flight flight) {
        super(cause);
        this.flight  = flight;
    }

    public FlightPublishException(String message, Throwable cause, final Flight flight) {
        super(message, cause);
        this.flight  = flight;
    }

    public FlightPublishException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace, final Flight flight) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.flight  = flight;
    }


}
