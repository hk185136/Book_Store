package com.eBook.Backend.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import com.eBook.Backend.Repository.CartAndOrderRepository;
import com.eBook.Backend.Repository.OrderHistoryRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.OrderHistory;

@SpringBootTest
public class OrderHistoryImplTest {

	@Autowired
	private OrderHistoryImplementation OrderHistoryImplementation;
	
	@MockBean
	private OrderHistoryRepository OrderHistoryRepository;
	
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
	
	
	OrderHistory OrderHistory1 = new OrderHistory("1",item1,"05-04-2024 15:30:10");
	OrderHistory OrderHistory2 = new OrderHistory("2",item2,"10-04-2024 20:10:05");
	OrderHistory OrderHistory3 = new OrderHistory("3",item3,"13-04-2024 20:10:00");
	Sort sort= Sort.by(Sort.Direction.DESC,"date");
	
	List<OrderHistory>OrderHistoryList = new ArrayList<>() {{add(OrderHistory2);add(OrderHistory3);}};
	List<OrderHistory>OrderHistoryList1 = new ArrayList<>() {{add(OrderHistory1);add(OrderHistory3);}};
	
	
	@BeforeEach
	public void setUp() {
		when(OrderHistoryRepository.save(OrderHistory1)).thenReturn(OrderHistory1);
		
		when(OrderHistoryRepository.findByUsername(user3.getUsername(), sort)).thenReturn(OrderHistoryList);
	}
	
	@Test
	public void addToHistory()
	{
		OrderHistory OrderHistoryActual = OrderHistoryImplementation.addtoHistory(OrderHistory1);
		assertAll(
				()->assertEquals(OrderHistory1.getDate(), OrderHistoryActual.getDate()),
				()->assertEquals(OrderHistory1.getItem().getUser().getUsername(), OrderHistoryActual.getItem().getUser().getUsername()),
				()->assertEquals(OrderHistory1.getItem().getBook().getTitle(), OrderHistoryActual.getItem().getBook().getTitle())				
				);
	}
	
	
	@Test
	public void findOrderHistoryByUsername()
	{
		List<OrderHistory> OrderHistoryActual = OrderHistoryImplementation.findOrderHistoryByUsername(user3.getUsername());
		for(int i=0;i<OrderHistoryActual.size();i++)
		{
			assertEquals(OrderHistoryList.get(i).getItem().getUser().getUsername(), OrderHistoryActual.get(i).getItem().getUser().getUsername());
		}

	}
}
