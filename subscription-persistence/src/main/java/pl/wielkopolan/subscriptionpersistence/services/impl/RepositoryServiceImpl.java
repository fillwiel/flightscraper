package pl.wielkopolan.subscriptionpersistence.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wielkopolan.subscriptionpersistence.data.domain.Subscription;
import pl.wielkopolan.subscriptionpersistence.data.dto.SubscriptionDto;
import pl.wielkopolan.subscriptionpersistence.data.repository.SubscriptionRepository;
import pl.wielkopolan.subscriptionpersistence.services.RepositoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription(subscriptionDto.getUserId(), subscriptionDto.getDeviceToken(),
                subscriptionDto.getArrivalCity(), subscriptionDto.getDepartureCity(), subscriptionDto.getPriceThreshold());
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> updateSubscription(String id, SubscriptionDto subscriptionDto) {
        return subscriptionRepository.findById(id).map(subscriptionRepository::save);
    }

    @Override
    public void deleteSubscriptionByIdInAndDeviceToken(List<String> ids, String deviceToken) {
        subscriptionRepository.deleteByIdInAndDeviceToken(ids, deviceToken);
    }

    @Override
    public void deleteSubscriptionByUserId(String userId) {
        subscriptionRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteSubscriptionByDeviceToken(String deviceToken) {
        subscriptionRepository.deleteByDeviceToken(deviceToken);
    }

    @Override
    public Optional<Subscription> getSubscriptionById(String id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public List<Subscription> getSubscriptionByUserId(String userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public List<SubscriptionDto> getByDeviceToken(String deviceToken) {
        List<Subscription> subscriptions = subscriptionRepository.findByDeviceToken(deviceToken);
        return subscriptions.stream().map(RepositoryServiceImpl::castSubscriptionToDto).toList();
    }

    @Override
    public List<SubscriptionDto> getByUserIdOrDeviceToken(String userId, String deviceToken) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserIdOrDeviceToken(userId, deviceToken);
        return subscriptions.stream().map(RepositoryServiceImpl::castSubscriptionToDto).toList();
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    private static SubscriptionDto castSubscriptionToDto(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(subscription.id());
        dto.setUserId(subscription.userId());
        dto.setDeviceToken(subscription.deviceToken());
        dto.setArrivalCity(subscription.arrivalCity());
        dto.setDepartureCity(subscription.departureCity());
        dto.setPriceThreshold(subscription.priceThreshold());
        return dto;
    }
}