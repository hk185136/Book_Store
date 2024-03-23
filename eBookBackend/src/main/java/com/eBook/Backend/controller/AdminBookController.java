package com.eBook.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eBook.Backend.Repository.BookRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.response.ErrorRes;
import com.eBook.Backend.models.response.LoginRes;
import com.eBook.Backend.service.AdminBookImplementation;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/books/")
@CrossOrigin(origins = "*")
public class AdminBookController {
	@Autowired
	private AdminBookImplementation adminBookImplementation;
	
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book)
	{
		Book savedBook = adminBookImplementation.createBook(book);
		return ResponseEntity.ok(savedBook);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") String bookId, @RequestBody Book book)
	{
		book.setId(bookId);
		Book updatedBook = adminBookImplementation.updateBook(book);
		return ResponseEntity.ok(updatedBook);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deleteBook(@PathVariable("id") String bookId, @RequestBody Book book)
	{
		adminBookImplementation.deleteBook(bookId);
		return ResponseEntity.status(HttpStatus.OK).body("Book was deleted successfully");
	}
}
