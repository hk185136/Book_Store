package com.eBook.Backend.controller;

import static org.junit.jupiter.api.Assertions.*;
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

import com.eBook.Backend.Repository.OrderhistoryRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.Orderhistory;
import com.eBook.Backend.service.OrderhistoryImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = OrderhistoryController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class OrderhistoryControllerTest 
{
   @MockBean
   OrderhistoryImplementation orderHistoryService;
   
   
   
   @Autowired
   MockMvc mockMvc;
	
	
	@Test
	public void test_addItemToHistory() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		Orderhistory order=new Orderhistory("1",item,"22 april 2024");
		when(orderHistoryService.addtoHistory(order)).thenReturn(order);
		mockMvc.perform(post("/api/user/orderhistory/addToHistory")
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
		List<Orderhistory> orderlist=Arrays.asList(new Orderhistory("1",item,"22 april 2024"),
				new Orderhistory("2",item2,"24 april 2024"));
		
		when(orderHistoryService.findOrderhistoryByUsername("maheen")).thenReturn(orderlist);
		mockMvc.perform(get("/api/user/orderhistory/getOrderHistory/{username}","maheen")
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
		List<Orderhistory> orderlist=Arrays.asList(new Orderhistory("1",item,"22 april 2024"),
				new Orderhistory("2",item2,"24 april 2024"));
		
		doNothing().when(orderHistoryService).deleteById("1");
		mockMvc.perform(delete("/api/user/orderhistory/{id}","1")
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
		List<Orderhistory> orderlist=Arrays.asList(new Orderhistory("1",item,"22 april 2024"),
				new Orderhistory("2",item2,"24 april 2024"));
		doNothing().when(orderHistoryService).deleteAll("maheen");
		mockMvc.perform(delete("/api/user/orderhistory/delete/{username}","maheen")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.content(new ObjectMapper().writeValueAsString(orderlist)))
		        .andExpect(status().isOk());
		
	}
	
	
	
	

}






