package com.eBook.Backend.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.eBook.Backend.Repository.BookRepository;
import com.eBook.Backend.models.Book;



@SpringBootTest

class UserBookImplementationTest {

	@Autowired
	private UserBookImplementation userBookService;
	
	@MockBean
	private BookRepository bookRepo;
	
	
	@BeforeEach
	void setUp()
	{
		
		
		Optional<Book> book1=Optional.of(new Book("1", "url1","Those Eyes", "james","horror", 1299, 5));
		
		Optional<List<Book>> list=Optional.of(new ArrayList<>());
		list.get().add(new Book("2", "url2","Those Eyes", "james","horror", 11500, 5));
		list.get().add(new Book("3", "url3","Mystery book", "ford","mystery", 1299, 6));
		
		Mockito.when(bookRepo.findByid("1")).thenReturn(book1);
		Mockito.when(bookRepo.findByAuthorStartsWithIgnoreCase("james")).thenReturn(list);
		
	}
	
	@Test
	void testGetBookById_Success() 
	{
		String title="Those Eyes";
		Book book=userBookService.getBookById("1");
		assertEquals(title,book.getTitle());
	}
	
	@Test
	void testGetBookByAuthor_Success()
	{
		String title="Those Eyes";
		Optional<List<Book>> booklist=userBookService.getBookByAuthor("james");
		assertEquals(title,booklist.get().get(0).getTitle());
		
	}
	
	
//	@Test
//	void testGetBookByGenre_Success()
//	{
//		String author="james";
//		Optional<List<Book>> booklist=userBookService.getBooksByGenre("horror");
//		assertEquals(author,booklist.get().get(0).getAuthor());
//		
//	}
//	
//	
//	@Test
//	void testGetBookByPrice_Success()
//	{
//		String title="Mystery Book";
//		Optional<List<Book>> booklist=userBookService.getBooksByPrice(1299);
//		assertEquals(title,booklist.get().get(0).getTitle());
//		
//	}
	
	
	
	
	
	

	
	
	

}
