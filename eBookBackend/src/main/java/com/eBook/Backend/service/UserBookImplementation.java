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
	
	public Optional<List<Book>> getBookByTitle(String bTitle) 
	{
		Optional<List<Book>> books=bookRepository.findByTitleStartsWithIgnoreCase(bTitle);
		return books;
	}
	
	
	public Optional<List<Book>> getBookByAuthor(String author) 
	{
		Optional<List<Book>> books=bookRepository.findByAuthorStartsWithIgnoreCase(author);
		return books;
	}
	
	public Optional<List<Book>> getBooksByGenre(String genre)
	{
		Optional<List<Book>> books=bookRepository.findByGenreIgnoreCase(genre);
				return books;
	}
	
	public Optional<List<Book>> getBooksByPrice(double price)
	{
		Optional<List<Book>> books=bookRepository.findByPrice(price);
				return books;
	}
	
	
	public Optional<List<Book>> getBooksByPriceLessorEqual(double price){
		
		Optional<List<Book>> books=bookRepository.findByPriceLessThanEqual(price);
		return books;
	}
	
public Optional<List<Book>> getBooksByPriceBetween(double p1,double p2){
		
		List<Book> books=bookRepository.findByPriceBetween(p1,p2).get();
		books.addAll(bookRepository.findByPrice(p1).get());
		books.addAll(bookRepository.findByPrice(p2).get());
		return Optional.of(books);
	}
	
	
	
}
