package pl.wielkopolan.notification.services;

import pl.wielkopolan.notification.data.FlightDto;
import pl.wielkopolan.notification.data.domain.Subscription;

import java.util.List;

public interface SubscriptionFinderService {
    List<Subscription> findMatchingSubscriptions(FlightDto flightDto);
}
