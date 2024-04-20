package com.eBook.Backend.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String>
{
	Optional<Book> findById(String id);
	Optional<List<Book>> findByTitleStartsWithIgnoreCase(String title);
	Optional<List<Book>> findByAuthorStartsWithIgnoreCase(String author);
	Optional<List<Book>> findByGenreIgnoreCase(String genre);
	Optional<List<Book>> findByPrice(double price);
	Optional<List<Book>> findByPriceLessThanEqual(double price);
	Optional<List<Book>> findByPriceBetween(double p1,double p2);
	Optional<List<Book>> findByAvailableQuantityGreaterThan(int availableQuantity);
}
