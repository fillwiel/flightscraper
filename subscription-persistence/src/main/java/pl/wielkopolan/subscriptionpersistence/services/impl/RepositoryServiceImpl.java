package pl.wielkopolan.subscriptionpersistence.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wielkopolan.subscriptionpersistence.data.domain.Subscription;
import pl.wielkopolan.subscriptionpersistence.data.dto.SubscriptionDto;
import pl.wielkopolan.subscriptionpersistence.data.repository.SubscriptionRepository;
import pl.wielkopolan.subscriptionpersistence.services.RepositoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public Subscription createSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription(subscriptionDto.getUserId(), subscriptionDto.getDeviceToken(),
                subscriptionDto.getArrivalCity(), subscriptionDto.getDepartureCity(), subscriptionDto.getPriceThreshold());
        return subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public void deleteSubscriptionByIdInAndDeviceToken(List<String> ids, String deviceToken) {
        subscriptionRepository.deleteByIdInAndDeviceToken(ids, deviceToken);
    }

    @Override
    @Transactional
    public void deleteSubscriptionByDeviceToken(String deviceToken) {
        subscriptionRepository.deleteByDeviceToken(deviceToken);
    }

    @Override
    public List<SubscriptionDto> getByDeviceToken(String deviceToken) {
        List<Subscription> subscriptions = subscriptionRepository.findByDeviceToken(deviceToken);
        return subscriptions.stream().map(RepositoryServiceImpl::castSubscriptionToDto).toList();
    }

    private static SubscriptionDto castSubscriptionToDto(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setUserId(subscription.userId());
        dto.setDeviceToken(subscription.deviceToken());
        dto.setArrivalCity(subscription.arrivalCity());
        dto.setDepartureCity(subscription.departureCity());
        dto.setPriceThreshold(subscription.priceThreshold());
        return dto;
    }
}