package pl.wielkopolan.subscriptionpersistence.services;

import pl.wielkopolan.subscriptionpersistence.data.domain.Subscription;
import pl.wielkopolan.subscriptionpersistence.data.dto.SubscriptionDto;

import java.util.List;

public interface RepositoryService {
    Subscription createSubscription(SubscriptionDto subscriptionDto);

    void deleteSubscriptionByIdInAndDeviceToken(List<String> ids, String deviceToken);

    void deleteSubscriptionByDeviceToken(String deviceToken);

    List<SubscriptionDto> getByDeviceToken(String deviceToken);
}