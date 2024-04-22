package com.eBook.Backend.service;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
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
class UserBookImplementationTest 
{
	@Autowired
	private UserBookImplementation userBookService;
	
	@MockBean
	private BookRepository bookRepo;
	
	//Setting up of responses for various operations using Mockito
	@BeforeEach
	void setUp()
	{
		Optional<Book> book=Optional.of(new Book("1", "url1","Those Eyes", "james","horror", 500, 5));
		Mockito.when(bookRepo.findById("1")).thenReturn(book);
		Optional<List<Book>> booksList=Optional.of(new ArrayList<>());
		booksList.get().add(new Book("2", "url2","Those Eyes", "james","horror", 500, 5));
		Mockito.when(bookRepo.findByAuthorStartsWithIgnoreCase("james")).thenReturn(booksList);

		
		booksList= Optional.of(new ArrayList<>());
		booksList.get().add(new Book("5","url5","The Great Gatsby", "Scott", "Romance",700, 8));
		Mockito.when(bookRepo.findByGenreIgnoreCase("romance")).thenReturn(booksList);
		
		
		booksList= Optional.of(new ArrayList<>());
		booksList.get().add(new Book("3", "url3","Mystery Book", "ford","mystery", 700, 6));
		Mockito.when(bookRepo.findByPrice(700)).thenReturn(booksList);
		
		booksList= Optional.of(new ArrayList<>());
		booksList.get().add(new Book("6", "url6","Chronicles of Narnia", "lewis","fantasy", 800, 5));
		Mockito.when(bookRepo.findByPrice(800)).thenReturn(booksList);
		

		booksList= Optional.of(new ArrayList<>());
		booksList.get().add(new Book("4","url4","Harry potter", "jk rowling", "fantasy",750, 10));
		Mockito.when(bookRepo.findByPriceBetween(700,800)).thenReturn(booksList);
		
		
	}
	
	//Test for finding a book by its id.
	@Test
	void testGetBookById_Success() 
	{
		String title="Those Eyes";
		Book book=userBookService.getBookById("1");
		assertEquals(title,book.getTitle());
	}
	
	//Tests for finding a book by its author
	@Test
	void testGetBookByAuthor_Success()
	{
		String title="Those Eyes";
		Optional<List<Book>> booklist=userBookService.getBookByAuthor("james");
		assertEquals(title,booklist.get().get(0).getTitle());	
	}
	
	@Test
	void testGetBookByAuthor_Failure()
	{
		String title="Animal Farm";
		Optional<List<Book>> booklist=userBookService.getBookByAuthor("james");
		assertNotEquals(title,booklist.get().get(0).getTitle());
		
	}
	
	
	//Tests for finding books using genre
	@Test
	void testGetBookByGenre_Success()
	{
		String title="The Great Gatsby";
		Optional<List<Book>> booklist=userBookService.getBooksByGenre("romance");
		assertEquals(title,booklist.get().get(0).getTitle());
		
	}
	
	@Test
	void testGetBookByGenre_Failure()
	{
		String title="Harry Potter";
		Optional<List<Book>> booklist=userBookService.getBooksByGenre("romance");
		assertNotEquals(title,booklist.get().get(0).getTitle());
		
	}
	
	
	//Tests for finding books filtered by price
	@Test
	void testGetBookByPrice_Success()
	{
		String title="Mystery Book";
		Optional<List<Book>> booklist=userBookService.getBooksByPrice(700);
		assertEquals(title,booklist.get().get(0).getTitle());
		
	}
	
	@Test
	void testGetBookByPrice_Failure()
	{
		String title="The Great Gatsby";
		Optional<List<Book>> booklist=userBookService.getBooksByPrice(700);
		assertNotEquals(title,booklist.get().get(0).getTitle());
		
	}
	
	
	@Test
	void testGetBookByPriceBetween_Success()
	{
		String title="Mystery Book";
		Optional<List<Book>> booklist=userBookService.getBooksByPriceBetween(700, 800);
		assertEquals(title,booklist.get().get(1).getTitle());
		
	}
	
	@Test
	void testGetBookByPriceBetween_Failure()
	{
		String title="Those Eyes";
		Optional<List<Book>> booklist=userBookService.getBooksByPriceBetween(700, 800);
		List<String>bookTitles = new ArrayList<>();
		bookTitles.add(booklist.get().get(0).getTitle());
		bookTitles.add(booklist.get().get(1).getTitle());
		bookTitles.add(booklist.get().get(2).getTitle());
		System.out.println(bookTitles);

		assertAll(()-> assertNotEquals(title,booklist.get().get(0).getTitle()),
				()-> assertNotEquals(title,booklist.get().get(1).getTitle()),
				()-> assertNotEquals(title,booklist.get().get(2).getTitle()));
	}
	
}
