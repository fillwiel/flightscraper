package pl.wielkopolan.flightpersistence.services;

import pl.wielkopolan.flightpersistence.data.FlightDto;

public interface FlightProcessingService {
    void processFlight(FlightDto flight);
}
