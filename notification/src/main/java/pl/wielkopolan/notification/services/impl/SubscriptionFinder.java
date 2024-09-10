package pl.wielkopolan.notification.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wielkopolan.notification.data.FlightDto;
import pl.wielkopolan.notification.data.domain.Subscription;
import pl.wielkopolan.notification.data.repository.SubscriptionRepository;
import pl.wielkopolan.notification.services.SubscriptionFinderService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscriptionFinder implements SubscriptionFinderService {
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Finds subscriptions in repository that match the flight
     * but ignores departure city field - subscriber is not required to specify it.
     * @param flightDto flight to be matched with subscriptions
     */
    @Override
    public List<Subscription> findMatchingSubscriptions(FlightDto flightDto) {
        return subscriptionRepository.findByArrivalCityAndPriceThresholdGreaterThan(flightDto.arrivalCity(),
                flightDto.currentPrice());
    }
}