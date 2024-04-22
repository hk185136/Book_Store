package com.eBook.Backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eBook.Backend.Repository.BookRepository;
import com.eBook.Backend.Repository.CartAndOrderRepository;
import com.eBook.Backend.config.OrderStatusConfig;
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
		if(cartAndOrderRepository.findByBooknameAndStatus(bookName,OrderStatusConfig.confirmed).size()!=0) {
			throw new Error("Book cannnot be deleted");
		}
		bookRepository.deleteById(bookId);
		return "Book deleted succesfully";
	}
}
