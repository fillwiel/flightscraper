package pl.wielkopolan.subscriptionpersistence.data.repository;

import com.mongodb.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.wielkopolan.subscriptionpersistence.data.domain.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findByUserId(String userId);
    Page<Subscription> getSubscriptionsByUserId(String userId, Pageable pageable);
    List<Subscription> findByDeviceToken(String deviceToken);
    List<Subscription> findByUserIdOrDeviceToken(String userId, String deviceToken);
    void deleteById(@NonNull String id);
    void deleteByIdAndDeviceToken(@NonNull String id, @NonNull String deviceToken);
    void deleteByIdInAndDeviceToken(@NonNull List<String> ids, @NonNull String deviceToken);
    void deleteByUserId(String userId);
    void deleteByDeviceToken(String deviceToken);
}
