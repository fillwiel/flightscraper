package pl.wielkopolan.notification.services;

import pl.wielkopolan.notification.data.FlightDto;

public interface FlightSubscriptionProcessingService {
    void notifyMatchingSubscribers(FlightDto flightDto);
}
