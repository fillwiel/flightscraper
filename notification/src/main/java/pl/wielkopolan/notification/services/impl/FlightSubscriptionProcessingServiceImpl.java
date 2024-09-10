package pl.wielkopolan.notification.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wielkopolan.notification.data.FlightDto;
import pl.wielkopolan.notification.data.domain.Subscription;
import pl.wielkopolan.notification.services.FlightSubscriptionProcessingService;
import pl.wielkopolan.notification.services.NotificationService;
import pl.wielkopolan.notification.services.SubscriptionFinderService;

@RequiredArgsConstructor
@Service
public class FlightSubscriptionProcessingServiceImpl implements FlightSubscriptionProcessingService {
    private final NotificationService notificationService;
    private final SubscriptionFinderService subscriptionFinderService;

    /**
     * Finds subscriptions in repository that match the flight
     * and sends push notification to the devices of found subscriptions.
     * @param flightDto flight to be matched with subscriptions
     */
    @Override
    public void notifyMatchingSubscribers(FlightDto flightDto) {
        subscriptionFinderService.findMatchingSubscriptions(flightDto)
                .stream()
                .filter(subscription -> hasMatchingDepartureCity(flightDto, subscription))
                .forEach(subscription -> notificationService.sendPushNotification(flightDto, subscription.deviceToken()));
    }

    /**
     * Checks if subscription departure city matches the flight departure city.
     * If subscription is empty, returns true (subscriber is interested in all departure cities).
     * @param flightDto used to match if subscription holds departure city field
     * @param subscription validated against optional departure city
     * @return true if subscriber is supposed to be notified
     */
    private static boolean hasMatchingDepartureCity(FlightDto flightDto, Subscription subscription) {
        return subscription.departureCity().isBlank() || subscription.departureCity().equals(flightDto.departureCity());
    }
}
