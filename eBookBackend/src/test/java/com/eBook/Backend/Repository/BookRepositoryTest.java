package com.eBook.Backend.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.eBook.Backend.models.Book;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest 
{
	@Autowired
	private BookRepository bookRepository;
	
	private Book book1,book2,book3,book4,book5;
	
	@BeforeAll
	public void setUp()
	{
		book1=new Book("1", "url1","Those Eyes", "james","horror", 500,20);
		book2= new Book("2","url2","The Great Gatsby", "Scott", "Romance",700, 20);
		book3=new Book("3", "url3","Mystery Book", "ford","mystery", 899, 20);
		book4=new Book("4","url4","Harry potter", "jk rowling", "fantasy",750, 20);
		book5 = new Book("5", "url5","Chronicles of Narnia", "lewis","fantasy", 800, 20);
	}
	
	List<Book> booklist=Arrays.asList((book1),(book2),(book3),(book4),(book5));
  	Optional<List<Book>> actualRes = Optional.of(booklist);
	
	
	@Test
	@Order(1)
	public void saveBookTest()
	{
		bookRepository.save(book1);
		bookRepository.save(book2);
		bookRepository.save(book3);
		bookRepository.save(book4);
		bookRepository.save(book5);	
		
		List<Book> booklist=bookRepository.findAll();
		//System.out.println(booklist.size());
		assertThat(booklist.size()).isGreaterThan(0);
	}
	
	@Test
	   public void test_FindBookbyId()
	   {
		  Book resbook= bookRepository.findByid("1").get();
		   
		   assertThat(resbook.getTitle()).isEqualTo("Those Eyes");
		   
	   }
	
	@Test
  public void test_findBookByTitle_StartsWithIgnoreCase()
  {
  	 
  	Optional<List<Book>> res=bookRepository.findByTitleStartsWithIgnoreCase("those Eyes");
  	
  	assertThat(res.get().get(0).getGenre()).isEqualTo("horror");  			
  }
	
	
	 @Test
   public void test_findByAuthor_ignoreCase()
   {
   
   	Optional<List<Book>> res=bookRepository.findByAuthorStartsWithIgnoreCase("ford");
   	assertThat(res.get().get(0).getTitle()).isEqualTo("Mystery Book");  
   }
   
   @Test
   public void test_findByPrice()
   {
   	
   	Optional<List<Book>> res=bookRepository.findByPrice(750);
   	assertThat(res.get().get(0).getTitle()).isEqualTo("Harry potter");
   }
   
   @Test
   public void test_findByprice_LessOrEqual()
   {
   	Optional<List<Book>> res=bookRepository.findByPriceLessThanEqual(500);
   	assertThat(res.get().size()).isGreaterThan(0);
   }
   
   
   @Test
   public void test_findByPriceBetween()
   {
   	
   	Optional<List<Book>> res=bookRepository.findByPriceBetween(700,800);
   	assertThat(res.get().get(0).getTitle()).isEqualTo("Harry potter");
   }
   

	@AfterAll
	public void reset() 
	{
		List<Book> deleteBooks=new ArrayList<>();
		deleteBooks.add(book1);
		deleteBooks.add(book2);
		deleteBooks.add(book3);
		deleteBooks.add(book4);
		deleteBooks.add(book5);
		
		bookRepository.deleteAll(deleteBooks);
	}
	
	
}
