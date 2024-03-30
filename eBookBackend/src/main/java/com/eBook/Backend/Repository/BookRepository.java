package com.eBook.Backend.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eBook.Backend.models.Book;
import java.util.List;


public interface BookRepository extends MongoRepository<Book, String>
{
	Optional<Book> findByid(String bid);
	Optional<List<Book>> findByTitleStartsWithIgnoreCase(String bTitle);
	Optional<List<Book>> findByAuthorStartsWithIgnoreCase(String bAuthor);
	Optional<List<Book>> findByGenreIgnoreCase(String bGenre);
	
	Optional<List<Book>> findByPrice(double bPrice);
	Optional<List<Book>> findByPriceLessThanEqual(double bPrice);
	Optional<List<Book>> findByPriceBetween(double p1,double p2);
}
