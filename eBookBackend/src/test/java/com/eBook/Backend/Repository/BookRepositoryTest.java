package com.eBook.Backend.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.eBook.Backend.models.Book;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest 
{


	    @Autowired
	    private BookRepository bookRepository;
	    
	    @Autowired
	    private TestEntityManager entityManager;

	   @BeforeEach
	   void setup()
	   {
		   Book book=new Book("1", "url1","Those Eyes", "james","horror", 11500, 5);
		   entityManager.persist(book);
		   
	   }
	   
	   @Test
	   public void testFindById()
	   {
		   Book book=bookRepository.findById("1").get();
		   assertEquals("Those Eyes", book.getTitle());
	   }

}
