package com.eBook.Backend.service;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import com.eBook.Backend.Repository.OrderHistoryRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.OrderHistory;

@SpringBootTest
public class OrderHistoryImplTest 
{
	@Autowired
	private OrderHistoryImplementation orderhistoryImplementation;
	
	@MockBean
	private OrderHistoryRepository orderhistoryRepository;
	
	Book book1=new Book("1", "url1","Those Eyes", "james","horror", 500,20);
	Book book2= new Book("2","url2","The Great Gatsby", "Scott", "Romance",700, 20);
	Book book3=new Book("3", "url3","Mystery Book", "ford","mystery", 700, 20);
	Book book4=new Book("4","url4","Harry potter", "jk rowling", "fantasy",750, 20);
	Book book5 = new Book("5", "url5","Chronicles of Narnia", "lewis","fantasy", 800, 20);
	Book newBook = new Book("3","url3","Animal Farm", "George Orwell", "Fantasy",450, 20);
	
	AuthUser user1 = new AuthUser("1","ranjan","12345","customer","123456789","hyderabad");
	AuthUser user2 = new AuthUser("2","admin","12345","admin",null,null);
	AuthUser user3 = new AuthUser("3","ranjanreddy","246810","customer","2468101214","hyderabad");

	
	Item item1 = new Item("1",book1,user1,10,"added to cart","05-04-2024");
	Item item2 = new Item("2",book2,user3,6,"pending","10-04-2024");
	Item item3 = new Item("3",book3,user3,15,"added to cart","13-04-2024");
	Item item4 = new Item("4",book4,user1,12,"added to cart","13-04-2024");
	Item item5 = new Item("5",book5,user1,9,"delivered","18-04-2024");
	Item invalidItem3 = new Item("3",book3,user1,25,"added to cart","13-04-2024");
	Item invalidItem5 = new Item("5",book5,user1,30,"delivered","18-04-2024");
	
	
	OrderHistory orderhistory1 = new OrderHistory("1",item1,"05-04-2024 15:30:10");
	OrderHistory orderhistory2 = new OrderHistory("2",item2,"10-04-2024 20:10:05");
	OrderHistory orderhistory3 = new OrderHistory("3",item3,"13-04-2024 20:10:00");
	Sort sort= Sort.by(Sort.Direction.DESC,"date");
	
	List<OrderHistory>orderhistoryList = new ArrayList<>() {{add(orderhistory2);add(orderhistory3);}};
	List<OrderHistory>orderhistoryList1 = new ArrayList<>() {{add(orderhistory1);add(orderhistory3);}};
	
	
	//Setting up of responses for various operations using Mockito
	@BeforeEach
	public void setUp() 
	{
		when(orderhistoryRepository.save(orderhistory1)).thenReturn(orderhistory1);
		
		when(orderhistoryRepository.findByUsername(user3.getUsername(), sort)).thenReturn(orderhistoryList);
	}
	
	//Test for pushing an order to history and recording the purchasing activity
	@Test
	public void addToHistorySuccess()
	{
		OrderHistory orderhistoryActual = orderhistoryImplementation.addtoHistory(orderhistory1);
		assertAll(
				()->assertEquals(orderhistory1.getDate(), orderhistoryActual.getDate()),
				()->assertEquals(orderhistory1.getItem().getUser().getUsername(), orderhistoryActual.getItem().getUser().getUsername()),
				()->assertEquals(orderhistory1.getItem().getBook().getTitle(), orderhistoryActual.getItem().getBook().getTitle())				
				);
	}
	
	//Test for the failure case of pushing an order to history
	@Test
	public void addToHistoryFailure()
	{
		OrderHistory orderhistoryActual = orderhistoryImplementation.addtoHistory(orderhistory1);
		assertAll(
				()->assertNotEquals(orderhistory2.getDate(), orderhistoryActual.getDate()),
				()->assertNotEquals(orderhistory2.getItem().getUser().getUsername(), orderhistoryActual.getItem().getUser().getUsername()),
				()->assertNotEquals(orderhistory2.getItem().getBook().getTitle(), orderhistoryActual.getItem().getBook().getTitle())				
				);
	}
	
	//Test for finding order history of a user
	@Test
	public void findOrderHistoryByUsernameSuccess()
	{
		List<OrderHistory> orderhistoryActual = orderhistoryImplementation.findOrderHistoryByUsername(user3.getUsername());
		for(int i=0;i<orderhistoryActual.size();i++)
		{
			assertEquals(orderhistoryList.get(i).getItem().getUser().getUsername(), orderhistoryActual.get(i).getItem().getUser().getUsername());
		}

	}
}
