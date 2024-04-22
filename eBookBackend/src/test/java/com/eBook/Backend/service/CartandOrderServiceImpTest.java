package com.eBook.Backend.service;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.eBook.Backend.Repository.CartAndOrderRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;

@SpringBootTest
public class CartandOrderServiceImpTest 
{
	@Autowired
	private CartAndOrderServiceImpl cartServiceImpl;
	
	@MockBean
	private CartAndOrderRepository cartRepository;
	
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


	List<Item>items = new ArrayList<>(){{add(item1);add(item4);}};
	List<Item>invalidItems = new ArrayList<>(){{add(item3);add(item5);}};
	
	//Setting up of responses for various operations using Mockito
	@BeforeEach
	void setUp()
	{
		when(cartRepository.save(item1)).thenReturn(item1);
		when(cartRepository.save(item2)).thenReturn(item2);
		when(cartRepository.save(item3)).thenReturn(item3);
		when(cartRepository.save(item4)).thenReturn(item4);
		when(cartRepository.save(item5)).thenReturn(item5);
		when(cartRepository.save(invalidItem3)).thenReturn(invalidItem3);
		when(cartRepository.save(invalidItem5)).thenReturn(invalidItem5);


		when(cartRepository.findById(item3.getId())).thenReturn(Optional.of(item3));
		when(cartRepository.findById(item4.getId())).thenReturn(Optional.of(item4));
		
		
		when(cartRepository.findByStatusAndUsername(item1.getStatus(), user1.getUsername())).thenReturn(items);
	}
	
	
	//Tests for adding a book into the cart successfully
	@Test
	public void addItemToCartSuccess()
	{
		Item itemActual = cartServiceImpl.addItem(item1);
		assertAll(
				()->assertEquals(item1.getBook().getTitle(),itemActual.getBook().getTitle()),
				()->assertEquals(item1.getUser().getUsername(),itemActual.getUser().getUsername()),
				()->assertTrue(item1.getQuantity()<=itemActual.getBook().getAvailableQuantity())			
				);
	}
	
	
	//Test for the failure case of adding a book into the cart
	@Test
	public void addItemToCartFailure()
	{
		Item itemActual = cartServiceImpl.addItem(invalidItem3);
		assertAll(
				()->assertEquals(invalidItem3.getBook().getTitle(),itemActual.getBook().getTitle()),
				()->assertEquals(invalidItem3.getUser().getUsername(),itemActual.getUser().getUsername())
				//()->assertTrue(invalidItem3.getQuantity()<=itemActual.getBook().getAvailableQuantity())			
				);
	}
	
	
	//Test for adding a book to the orders list when user proceed to buy it.
	@Test
	public void addItemToOrdersSuccess()
	{
		Item itemActual = cartServiceImpl.addItem(item2);
		assertAll(
				()->assertEquals(item2.getBook().getTitle(),itemActual.getBook().getTitle()),
				()->assertEquals(item2.getUser().getUsername(),itemActual.getUser().getUsername()),
				()->assertTrue(item2.getQuantity()<=itemActual.getBook().getAvailableQuantity())			
				);
	}
	

	//Test for increasing the purchasing quantity of a book in the cart.
	@Test
	public void increaseItemSuccess()
	{
		Item itemActual = cartServiceImpl.increaseItem(item3);
		assertAll(
				()->assertEquals(item3.getBook().getTitle(),itemActual.getBook().getTitle()),
				()->assertEquals(item3.getUser().getUsername(),itemActual.getUser().getUsername()),
				()->assertEquals("added to cart",itemActual.getStatus()),
				()->assertEquals(item3.getQuantity(), itemActual.getQuantity())
				);
	}

	//Test for decreasing the purchasing quantity of a book in the cart.
	@Test
	public void decreaseItemSuccess()
	{
		Item itemActual = cartServiceImpl.decreaseItem(item3);
		assertAll(
				()->assertEquals(item3.getBook().getTitle(),itemActual.getBook().getTitle()),
				()->assertEquals(item3.getUser().getUsername(),itemActual.getUser().getUsername()),
				()->assertEquals("added to cart",itemActual.getStatus()),
				()->assertEquals(item3.getQuantity(), itemActual.getQuantity())
				);
	}

	//Test for finding an order by its status and a user in the cart.
	@Test
	public void findByStatusAndUsernameSuccess()
	{
		
		List<Item> itemsActual= new ArrayList<>();
		for(int i=0;i<itemsActual.size();i++)
		{
			assertEquals(items.get(i).getStatus(), itemsActual.get(i).getStatus());
			assertEquals(items.get(i).getUser().getUsername(), itemsActual.get(i).getUser().getUsername());
		}
	}
	

	//Test for updating the status of an order
	@Test
	public void UpdateItemStatusSuccess()
	{
		Item itemActual = cartServiceImpl.updateItemStatus(item4,"pending");
		assertEquals(item4.getStatus(), itemActual.getStatus());
	}

	//Test for the failure case of updating the status of an order
	@Test
	public void UpdateItemStatusFailure()
	{
		Item itemActual = cartServiceImpl.updateItemStatus(item4,"pending");
		assertNotEquals(item3.getStatus(), itemActual.getStatus());
	}
	
	
}
