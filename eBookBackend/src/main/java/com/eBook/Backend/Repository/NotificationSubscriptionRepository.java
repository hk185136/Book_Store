package com.eBook.Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eBook.Backend.models.NotificationSubscription;

public interface NotificationSubscriptionRepository extends MongoRepository<NotificationSubscription, String>{
	@Query(value = "{'book.title': ?0}")
	Optional<List<NotificationSubscription>> findByTitle(String title);
	
	@Query(value = "{'item.status': ?0, 'item.book.title': ?1}")
	Optional<List<NotificationSubscription>> findByStatusAndTitle(String status, String title);
	
	@Query(value = "{'user.username': ?0}")
	Optional<List<NotificationSubscription>> findByUsername(String username);
		
}
