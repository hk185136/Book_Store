package com.eBook.Backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eBook.Backend.models.Message;
import java.util.List;
import java.util.Optional;


public interface MessageRepository extends MongoRepository<Message, String>{
	Optional<List<Message>> findByUsername(String username);
}
