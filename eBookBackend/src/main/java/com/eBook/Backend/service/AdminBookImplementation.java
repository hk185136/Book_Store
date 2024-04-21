package com.eBook.Backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.BookRepository;
import com.eBook.Backend.Repository.CartAndOrderRepository;
import com.eBook.Backend.models.Book;

@Service
public class AdminBookImplementation {
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CartAndOrderRepository cartAndOrderRepository;
	
	public Book createBook(Book book) {
		return bookRepository.save(book);
	}
	

	public Book updateBook(Book book) {
		Book existingBook= bookRepository.findById(book.getId()).get();
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setGenre(book.getGenre());
		existingBook.setPrice(book.getPrice());
		existingBook.setUrl(book.getUrl());
		existingBook.setAvailableQuantity(book.getAvailableQuantity());
		Book updatedBook = bookRepository.save(existingBook);
		return updatedBook;
	}
	
	public String deleteBook(String bookId) {
		String bookName = bookRepository.findById(bookId).get().getTitle();
		return cartAndOrderRepository.findByBookname(bookName).size()!=0?"Book cannnot be deleted":"Book deleted succesfully";
	}
}
