package pl.wielkopolan.subscriptionpersistence.data.repository;

import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wielkopolan.subscriptionpersistence.data.domain.Subscription;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findByDeviceToken(String deviceToken);
    void deleteById(@NonNull String id);
    void deleteByIdInAndDeviceToken(@NonNull List<String> ids, @NonNull String deviceToken);
    void deleteByDeviceToken(String deviceToken);
}
