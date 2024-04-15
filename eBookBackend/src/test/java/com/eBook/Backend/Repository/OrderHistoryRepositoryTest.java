package com.eBook.Backend.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.OrderHistory;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderHistoryRepositoryTest {
	@Autowired
	private OrderHistoryRepository OrderHistoryRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Autowired
	private CartAndOrderRepository cartRepository;
	
	
	private Book book1,book2,book3,book4,book5;
	private AuthUser user1,user2,user3;
	private Item item1,item2,item3,item4,item5;
	private OrderHistory OrderHistory1,OrderHistory2,OrderHistory3;
	private Sort sort= Sort.by(Sort.Direction.DESC,"date");

	List<OrderHistory>OrderHistoryList ;
	@BeforeAll
	public void setUp()
	{
		book1=new Book("1", "url1","Those Eyes", "james","horror", 500,20);
		book2= new Book("2","url2","The Great Gatsby", "Scott", "Romance",700, 20);
		book3=new Book("3", "url3","Mystery Book", "ford","mystery", 700, 20);
		book4=new Book("4","url4","Harry potter", "jk rowling", "fantasy",750, 20);
		book5 = new Book("5", "url5","Chronicles of Narnia", "lewis","fantasy", 800, 20);
		
		user1 = new AuthUser("1","ranjan","12345","customer","123456789","hyderabad");
		user2 = new AuthUser("2","admin","12345","admin",null,null);
		user3 = new AuthUser("3","ranjanreddy","246810","customer","2468101214","hyderabad");
		
		
		item1 = new Item("1",book1,user1,10,"added to cart","05-04-2024");
		item2 = new Item("2",book2,user3,6,"pending","10-04-2024");
		item3 = new Item("3",book3,user3,15,"added to cart","13-04-2024");
		item4 = new Item("4",book4,user1,12,"added to cart","13-04-2024");
		item5 = new Item("5",book5,user1,9,"delivered","18-04-2024");	
		
		OrderHistory1 = new OrderHistory("1",item1,"05-04-2024 15:30:10");
		OrderHistory2 = new OrderHistory("2",item2,"10-04-2024 20:10:05");
		OrderHistory3 = new OrderHistory("3",item3,"13-04-2024 20:10:00");
		
	}
	
	@Test
	@Order(1)
	public void saveItemTest()
	{
		
		cartRepository.save(item1);
		cartRepository.save(item2);
		cartRepository.save(item3);
		cartRepository.save(item4);
		cartRepository.save(item5);
		
		authUserRepository.save(user1);
		authUserRepository.save(user2);
		authUserRepository.save(user3);
		
		bookRepository.save(book1);
		bookRepository.save(book2);
		bookRepository.save(book3);
		bookRepository.save(book4);
		bookRepository.save(book5);	
		
		OrderHistoryRepository.save(OrderHistory1);
		OrderHistoryRepository.save(OrderHistory2);
		OrderHistoryRepository.save(OrderHistory3);
		
		
		List<OrderHistory> OrderHistoryList=OrderHistoryRepository.findAll();
		
		assertThat(OrderHistoryList.size()).isGreaterThan(0);
	}
	
	@Test
	public void getHistoryByUsername()
	{
		List<OrderHistory> userHistoryActual = OrderHistoryRepository.findByUsername("ranjan",sort);
		
		assertThat(userHistoryActual.size()).isGreaterThan(0);
	}
	
	
	@AfterAll
	public void reset() {
		List<Item>itemsDel = new ArrayList<>();
		itemsDel.add(item1);
		itemsDel.add(item2);
		itemsDel.add(item3);
		itemsDel.add(item4);
		itemsDel.add(item5);
		cartRepository.deleteAll(itemsDel);
		
		List<AuthUser>userDel = new ArrayList<>();
		userDel.add(user1);
		userDel.add(user2);
		userDel.add(user3);

		authUserRepository.deleteAll(userDel);
		
		List<Book>bookDel = new ArrayList<>();
		bookDel.add(book1);
		bookDel.add(book2);
		bookDel.add(book3);
		bookDel.add(book4);
		bookDel.add(book5);
		
		bookRepository.deleteAll(bookDel);
		
		
		List<OrderHistory>historyDel = new ArrayList<>();
		historyDel.add(OrderHistory1);
		historyDel.add(OrderHistory2);
		historyDel.add(OrderHistory3);
		
		OrderHistoryRepository.deleteAll(historyDel);
		
		
	}
}
