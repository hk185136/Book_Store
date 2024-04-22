package com.eBook.Backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;

import java.util.List;



public interface CartAndOrderRepository extends MongoRepository<Item, String>
{
	List<Item> findByUser(AuthUser user);
	List<Item> findByStatus(String status);
	
	//Fetches item by status and user
	@Query(value = "{'status':?0, 'user.username': ?1}")
	List <Item> findByStatusAndUsername(String status,String username);

	//Fetches item by book and status
	List<Item> findByBookAndStatus(Book book, String status);
	
	//Fetches item by  bookname
	@Query(value = "{'book.title': ?0}")	
	List<Item> findByBookname(String bookname);
	
	//fetches item by bookname and status
	@Query(value = "{'book.title': ?0, 'status' : ?1}")
	List<Item> findByBooknameAndStatus(String bookname,String status);
}
