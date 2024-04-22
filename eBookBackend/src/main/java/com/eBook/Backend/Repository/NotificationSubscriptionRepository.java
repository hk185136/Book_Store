package com.eBook.Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eBook.Backend.models.NotficationSubscription;

public interface NotificationSubscriptionRepository extends MongoRepository<NotficationSubscription, String>{
	@Query(value = "{'book.title': ?0}")
	Optional<List<NotficationSubscription>> findByTitle(String title);
	
	@Query(value = "{'item.status': ?0, 'item.book.title': ?1}")
	Optional<List<NotficationSubscription>> findByStatusAndTitle(String status, String title);
	
	@Query(value = "{'user.username': ?0}")
	Optional<List<NotficationSubscription>> findByUsername(String username);
		
}
