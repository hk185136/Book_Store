package com.eBook.Backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.BookRepository;
import com.eBook.Backend.models.Book;

@Service
public class AdminBookImplementation {
	@Autowired
	private BookRepository bookRespository;
	
	public Book createBook(Book book) {
		return bookRespository.save(book);
	}
	

	public Book updateBook(Book book) {
		Book existingBook= bookRespository.findByid(book.getId()).get();
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setGenre(book.getGenre());
		existingBook.setPrice(book.getPrice());
		existingBook.setUrl(book.getUrl());
		existingBook.setAvailableQuantity(book.getAvailableQuantity());
		Book updatedBook = bookRespository.save(existingBook);
		return updatedBook;
	}
	
	public void deleteBook(String bookId) {
		bookRespository.deleteById(bookId);
	}
}
