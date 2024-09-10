package pl.wielkopolan.notification.services.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wielkopolan.notification.data.FlightDto;
import pl.wielkopolan.notification.services.NotificationService;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendPushNotification(FlightDto flightDto, String deviceToken) {
        Message message = Message.builder()
                .setNotification(buildNotification(flightDto))
                .setToken(deviceToken)
                .build();
        try {
            String response = firebaseMessaging.send(message);
            log.debug("Successfully sent message with ID: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }

    private static Notification buildNotification(FlightDto flightDto) {
        String body = "Price for flight to: " + flightDto.arrivalCity() +
                " from: " + flightDto.departureCity() +
                "dropped to: " + flightDto.currentPrice();
        return Notification.builder()
                .setTitle("Flight price drop!")
                .setBody(body)
                .build();
    }
}