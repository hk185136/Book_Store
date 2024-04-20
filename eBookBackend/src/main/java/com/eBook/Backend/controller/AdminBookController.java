package com.eBook.Backend.controller;

import com.eBook.Backend.service.AdminBookImplementation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.eBook.Backend.models.Book;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/books/")
@CrossOrigin(origins = "*")
//Class to implement rest APIS for admin side book store management
public class AdminBookController {
	@Autowired
	//Auto wiring the service implementation layer class 
	private AdminBookImplementation adminBookImplementation;
	
	@PostMapping
	//Post request which accepts book data, stores that data in database and returns that book data.
	public ResponseEntity<Book> createBook(@RequestBody Book book)
	{
		Book savedBook = adminBookImplementation.createBook(book);
		return ResponseEntity.ok(savedBook);
	}
	
	@PutMapping("{id}")
	//Put request which accepts new book data, an existing book id, updates the book with that id with the new book data and returns the updated book data.
	public ResponseEntity<Book> updateBook(@PathVariable("id") String bookId, @RequestBody Book book)
	{
		book.setId(bookId);
		Book updatedBook = adminBookImplementation.updateBook(book);
		return ResponseEntity.ok(updatedBook);
	}
	
	@DeleteMapping("{id}")
	//Delete request which accepts book id, deletes a book with that id and returns a success message.
	public ResponseEntity<String> deleteBook(@PathVariable("id") String bookId)
	{
		String message = adminBookImplementation.deleteBook(bookId);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
