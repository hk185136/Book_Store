package com.eBook.Backend.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.eBook.Backend.Repository.BookRepository;
import com.eBook.Backend.models.Book;



@SpringBootTest
public class AdminBookImplementationTest
{
    @Autowired
    private AdminBookImplementation adminBookImplementation;
    
    @MockBean
    private BookRepository bookRepository;
    
    Book book1=new Book("1", "url1","Those Eyes", "james","horror", 500, 5);
	Book book2= new Book("2","url2","The Great Gatsby", "Scott", "Romance",700, 8);
	Optional<Book>book3=Optional.of(new Book("3", "url3","Mystery Book", "ford","mystery", 700, 6));
	Optional<Book>book4= Optional.of(new Book("4","url4","Harry potter", "jk rowling", "fantasy",750, 10));
	Book book6 = new Book("6", "url6","Chronicles of Narnia", "lewis","fantasy", 800, 5);
	Book newBook = new Book("3","url3","Animal Farm", "George Orwell", "Fantasy",450, 10);

    
	@BeforeEach
	void setUp() {
		Mockito.when(bookRepository.save(book1)).thenReturn(book1);

		Mockito.when(bookRepository.save(book3.get())).thenReturn(book3.get());

		Mockito.when(bookRepository.findByid("3")).thenReturn(book3);
		
		Mockito.when(bookRepository.findByid("4")).thenReturn(book4);
		
	}
	
	@Test
	void create_Success()
	{
		Book bookActual= adminBookImplementation.createBook(book1);
		assertEquals( book1.getTitle(),bookActual.getTitle());
	}
	
//	@Test
//	void create_Failure()
//	{
//		Book bookActual= adminBookImplementation.createBook(book1);
//		assertEquals( book2.getTitle(),bookActual.getTitle());
//	}
	
	@Test
	void update_Success()
	{
		Book bookActual =adminBookImplementation.updateBook(newBook);
		assertEquals(newBook.getTitle(),bookActual.getTitle());
	}
	
//	@Test
//	void update_Failure()
//	{
//		Book bookActual =adminBookImplementation.updateBook(newBook);
//		assertEquals( book6.getTitle(),bookActual.getTitle());
//	}
}

