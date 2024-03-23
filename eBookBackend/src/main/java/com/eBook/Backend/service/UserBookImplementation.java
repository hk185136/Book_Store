package com.eBook.Backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.BookRepository;
import com.eBook.Backend.models.Book;


@Service
public class UserBookImplementation {
	@Autowired
	private BookRepository bookRepository;
	
	public Book getBookById(String bookId) {
		Optional<Book>optionalBook = bookRepository.findByid(bookId);
		return optionalBook.get();
	}
	
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
}
