package com.eBook.Backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eBook.Backend.models.Notification;
import java.util.List;
import java.util.Optional;


public interface NotificationRepository extends MongoRepository<Notification, String>{
	Optional<List<Notification>> findByUsername(String username);
	
}
