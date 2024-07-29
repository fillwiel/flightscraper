package pl.wielkopolan.subscriptionpersistence.services;

import pl.wielkopolan.subscriptionpersistence.data.domain.Subscription;
import pl.wielkopolan.subscriptionpersistence.data.dto.SubscriptionDto;

import java.util.List;
import java.util.Optional;

public interface RepositoryService {
    Subscription createSubscription(SubscriptionDto subscriptionDto);

    Optional<Subscription> updateSubscription(String id, SubscriptionDto subscriptionDto);

    void deleteSubscriptionByIdInAndDeviceToken(List<String> ids, String deviceToken);

    void deleteSubscriptionByUserId(String userId);

    void deleteSubscriptionByDeviceToken(String deviceToken);

    Optional<Subscription> getSubscriptionById(String id);

    List<Subscription> getSubscriptionByUserId(String userId);

    List<SubscriptionDto> getByDeviceToken(String deviceToken);

    List<SubscriptionDto> getByUserIdOrDeviceToken(String userId, String deviceToken);

    List<Subscription> getAllSubscriptions();
}