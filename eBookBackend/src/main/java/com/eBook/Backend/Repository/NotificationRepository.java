package com.eBook.Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>{
	@Query(value = "{'book.title': ?0, 'user.username': ?1}")
	Optional<Notification> findByTitleAndUsername(String title, String username);
	
	@Query(value = "{'item.status': ?0, 'item.user.username': ?1, 'item.book.title': ?2}")
	Optional<Notification> findByStatusAndUsernameAndTitle(String status, String username, String title);
}
