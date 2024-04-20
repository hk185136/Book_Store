package com.eBook.Backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eBook.Backend.models.Book;
import com.eBook.Backend.service.UserBookImplementation;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/books/")
@CrossOrigin(origins = "*")
public class UserBookController {
	@Autowired
	private UserBookImplementation userBookImplementation;
	
	@GetMapping("{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") String bookId){
		Book book = userBookImplementation.getBookById(bookId);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks(){
		List<Book>bookList = userBookImplementation.getAllBooks();
		return ResponseEntity.ok(bookList);
	}
	
	@GetMapping("title/{title}")
	 public ResponseEntity<Optional<List<Book>>> getBookByTitle(@PathVariable("title") String btitle){
		Optional<List<Book>> book=userBookImplementation.getBookByTitle(btitle);
        return ResponseEntity.ok(book);
	}
	
	@GetMapping("author/{author}")
	 public ResponseEntity<Optional<List<Book>>> getBookByAuthor(@PathVariable("author") String author){
		Optional<List<Book>> book=userBookImplementation.getBookByAuthor(author);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("genre/{genre}")
	 public ResponseEntity<Optional<List<Book>>> getBookByGenre(@PathVariable("genre") String genre){
		Optional<List<Book>> book=userBookImplementation.getBooksByGenre(genre);
		return ResponseEntity.ok(book);
	}
	
	
	@GetMapping("price/{price}")
	 public ResponseEntity<Optional<List<Book>>> getBookByPrice(@PathVariable("price") double price){
		Optional<List<Book>> book=userBookImplementation.getBooksByPrice(price);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("lessorequalprice/{price}")
	 public ResponseEntity<Optional<List<Book>>> getBooksByPriceLessorEqual(@PathVariable("price") double price){
		Optional<List<Book>> book=userBookImplementation.getBooksByPriceLessorEqual(price);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("pricerange/{p1}/{p2}")
	 public ResponseEntity<Optional<List<Book>>> getBooksByPriceBetween(@PathVariable("p1") double p1,@PathVariable("p2") double p2){
		Optional<List<Book>> book=userBookImplementation.getBooksByPriceBetween(p1,p2);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("pricerange/{availableQuantity}")
	 public ResponseEntity<Optional<List<Book>>> getBooksByStock(@PathVariable int availableQuantity){
		Optional<List<Book>> book=userBookImplementation.getBooksByStock(availableQuantity);
		return ResponseEntity.ok(book);
	}
	
	
	
}
