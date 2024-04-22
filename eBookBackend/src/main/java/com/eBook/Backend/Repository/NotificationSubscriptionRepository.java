package com.eBook.Backend.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.eBook.Backend.models.NotificationSubscription;


// Helps in CRUD operations related to subscriptions for notifications.
public interface NotificationSubscriptionRepository extends MongoRepository<NotificationSubscription, String>{
	// Fetches subscriptions for book re-stock based on book title.
	@Query(value = "{'book.title': ?0}")
	Optional<List<NotificationSubscription>> findByTitle(String title);

	// Fetches subscriptions for book re-stock for a user.
	@Query(value = "{'user.username': ?0}")
	Optional<List<NotificationSubscription>> findByUsername(String username);
}
