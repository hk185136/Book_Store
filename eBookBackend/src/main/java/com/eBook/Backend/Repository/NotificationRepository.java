package com.eBook.Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>{
	@Query(value = "{'book.title': ?0}")
	Optional<List<Notification>> findByTitle(String title);
	
	@Query(value = "{'item.status': ?0, 'item.book.title': ?1}")
	Optional<List<Notification>> findByStatusAndTitle(String status, String title);
	
	@Query(value = "{'user.username': ?0}")
	Optional<List<Notification>> findByUsername(String username);
}
