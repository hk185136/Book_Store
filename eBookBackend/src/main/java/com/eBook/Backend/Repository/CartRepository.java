package com.eBook.Backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends MongoRepository<Item, String>{
	List<Item> findByUser(AuthUser user);
	
}
