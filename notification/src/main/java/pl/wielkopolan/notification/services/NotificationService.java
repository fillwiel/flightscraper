package pl.wielkopolan.notification.services;

import pl.wielkopolan.notification.data.FlightDto;

public interface NotificationService {
    void sendPushNotification(FlightDto flightDto, String deviceToken);
}
