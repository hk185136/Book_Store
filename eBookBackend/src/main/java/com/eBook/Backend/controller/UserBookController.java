package com.eBook.Backend.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
// Manages all user operations related to books.
public class UserBookController {
	
	// Auto wiring service layer.
	@Autowired
	private UserBookImplementation userBookImplementation;
	
	
	// Get a book based on an ID.
	@GetMapping("{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") String bookId){
		Book book = userBookImplementation.getBookById(bookId);
		return ResponseEntity.ok(book);
	}
	
	// Get all books stored in the database.
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks(){
		List<Book>bookList = userBookImplementation.getAllBooks();
		return ResponseEntity.ok(bookList);
	}
	
	// Get books based on a title.
	@GetMapping("title/{title}")
	 public ResponseEntity<Optional<List<Book>>> getBookByTitle(@PathVariable("title") String title){
		Optional<List<Book>> book=userBookImplementation.getBookByTitle(title);
        return ResponseEntity.ok(book);
	}
	
	// Get books based on an author.
	@GetMapping("author/{author}")
	 public ResponseEntity<Optional<List<Book>>> getBookByAuthor(@PathVariable("author") String author){
		Optional<List<Book>> book=userBookImplementation.getBookByAuthor(author);
		return ResponseEntity.ok(book);
	}
	
	// Get books based on a genre.
	@GetMapping("genre/{genre}")
	 public ResponseEntity<Optional<List<Book>>> getBookByGenre(@PathVariable("genre") String genre){
		Optional<List<Book>> book=userBookImplementation.getBooksByGenre(genre);
		return ResponseEntity.ok(book);
	}
	
	// Get books based on a price.
	@GetMapping("price/{price}")
	 public ResponseEntity<Optional<List<Book>>> getBookByPrice(@PathVariable("price") double price){
		Optional<List<Book>> book=userBookImplementation.getBooksByPrice(price);
		return ResponseEntity.ok(book);
	}
	
	// Get books whose price is less than or equal to a particular price.
	@GetMapping("lessorequalprice/{price}")
	 public ResponseEntity<Optional<List<Book>>> getBooksByPriceLessorEqual(@PathVariable("price") double price){
		Optional<List<Book>> book=userBookImplementation.getBooksByPriceLessorEqual(price);
		return ResponseEntity.ok(book);
	}
	
	// Get books whose price is in a particular price range.
	@GetMapping("pricerange/{p1}/{p2}")
	 public ResponseEntity<Optional<List<Book>>> getBooksByPriceBetween(@PathVariable("p1") double p1,@PathVariable("p2") double p2){
		Optional<List<Book>> book=userBookImplementation.getBooksByPriceBetween(p1,p2);
		return ResponseEntity.ok(book);
	}
}
