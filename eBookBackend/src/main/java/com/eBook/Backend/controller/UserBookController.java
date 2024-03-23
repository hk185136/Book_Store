package com.eBook.Backend.controller;

import java.util.List;

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
	
	
}
