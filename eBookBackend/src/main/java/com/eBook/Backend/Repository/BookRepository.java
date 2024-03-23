package com.eBook.Backend.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eBook.Backend.models.Book;
import java.util.List;


public interface BookRepository extends MongoRepository<Book, String>{
	Optional<Book> findByid(String bid);
	Optional<Book> findByTitle(String bTitle);
	Optional<Book> findByAuthor(String bAuthor);
	Optional<Book> findByPrice(double bPrice);
	Optional<Book> findByGenre(String bGenre);
}
