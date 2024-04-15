package com.eBook.Backend.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.OrderHistory;
import com.eBook.Backend.service.OrderHistoryImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = OrderHistoryController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class OrderHistoryControllerTest 
{
   @MockBean
   OrderHistoryImplementation OrderHistoryService;
   
   
   
   @Autowired
   MockMvc mockMvc;
	
	
	@Test
	public void test_addItemToHistory() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		OrderHistory order=new OrderHistory("1",item,"22 april 2024");
		when(OrderHistoryService.addtoHistory(order)).thenReturn(order);
		mockMvc.perform(post("/api/user/OrderHistory/addToHistory")
		.contentType(MediaType.APPLICATION_JSON)
		.with(csrf())
        .content(new ObjectMapper().writeValueAsString(order)))
        .andExpect(status().isOk());
	}
	
	
	@Test
	public void test_getOrderHistory() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		Book book2=new Book("1","url","Hanging House","Ford","horror",5299,5);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		Item item2=new Item("2",book2,user,2,"pending","10 April 2024");
		List<OrderHistory> orderlist=Arrays.asList(new OrderHistory("1",item,"22 april 2024"),
				new OrderHistory("2",item2,"24 april 2024"));
		
		when(OrderHistoryService.findOrderHistoryByUsername("maheen")).thenReturn(orderlist);
		mockMvc.perform(get("/api/user/OrderHistory/getOrderHistory/{username}","maheen")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.content(new ObjectMapper().writeValueAsString(orderlist)))
		        .andExpect(status().isOk());
	}
	
	
	@Test
	public void test_deleteOrderHistory() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		Book book2=new Book("1","url","Hanging House","Ford","horror",5299,5);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		Item item2=new Item("2",book2,user,2,"pending","10 April 2024");
		List<OrderHistory> orderlist=Arrays.asList(new OrderHistory("1",item,"22 april 2024"),
				new OrderHistory("2",item2,"24 april 2024"));
		
		doNothing().when(OrderHistoryService).deleteById("1");
		mockMvc.perform(delete("/api/user/OrderHistory/{id}","1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.content(new ObjectMapper().writeValueAsString(orderlist)))
		        .andExpect(status().isOk());
	}
	
	
	@Test
	public void test_deleteAllOrderHistory() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		Book book2=new Book("1","url","Hanging House","Ford","horror",5299,5);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		Item item2=new Item("2",book2,user,2,"pending","10 April 2024");
		List<OrderHistory> orderlist=Arrays.asList(new OrderHistory("1",item,"22 april 2024"),
				new OrderHistory("2",item2,"24 april 2024"));
		doNothing().when(OrderHistoryService).deleteAll("maheen");
		mockMvc.perform(delete("/api/user/OrderHistory/delete/{username}","maheen")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.content(new ObjectMapper().writeValueAsString(orderlist)))
		        .andExpect(status().isOk());
		
	}
	
	
	
	

}






