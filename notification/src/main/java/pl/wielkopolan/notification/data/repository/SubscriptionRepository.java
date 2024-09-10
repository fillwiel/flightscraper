package pl.wielkopolan.notification.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wielkopolan.notification.data.domain.Subscription;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findByArrivalCityAndPriceThresholdGreaterThan(String arrivalCity, int priceThreshold);
}
