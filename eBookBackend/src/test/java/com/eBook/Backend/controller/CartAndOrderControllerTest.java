package com.eBook.Backend.controller;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import com.eBook.Backend.service.CartAndOrderServiceImpl;
import com.eBook.Backend.service.OrderHistoryImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = CartAndOrderController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class CartControllerTest
{
    
	@MockBean
	CartAndOrderServiceImpl cartService;
	
	@MockBean
	private OrderHistoryImplementation orderhistoryImplementation;
	
	@Autowired
	MockMvc mockMvc;
	
	//Testing the controller which adds an item to the cart
	@Test
	public void test_AddItemToCart() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		when(cartService.addItem(item)).thenReturn(item);
		mockMvc.perform(post("/api/item/addToCart")
		.contentType(MediaType.APPLICATION_JSON)
		.with(csrf())
        .content(new ObjectMapper().writeValueAsString(book)))
        .andExpect(status().isOk());
		
	}
	
	//Testing the controller which adds an item to orders when user proceed to buy from the cart
	@Test
	public void test_addItemToOrder() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		when(cartService.addItem(item)).thenReturn(item);
		mockMvc.perform(post("/api/item/addToOrder")
		.contentType(MediaType.APPLICATION_JSON)
		.with(csrf())
        .content(new ObjectMapper().writeValueAsString(book)))
        .andExpect(status().isOk());
		
	}
	
	//Testing the controller for updating an item status in the cart
	@Test
	public void test_updateItemStatus() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		when(cartService.updateItemStatus(item,"delivered")).thenReturn(item);
		mockMvc.perform(put("/api/item/updateStatus/{status}","delivered")
		.contentType(MediaType.APPLICATION_JSON)
		.with(csrf())
        .content(new ObjectMapper().writeValueAsString(item)))
        .andExpect(status().isOk());
		
	}
	
	//Testing the controller for increasing item's purchasing quantity in the cart
	@Test
	public void test_increaseItem() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		when(cartService.increaseItem(item)).thenReturn(item);
		mockMvc.perform(put("/api/item/{id}/increase","1")
				.contentType(MediaType.APPLICATION_JSON)
		        .with(csrf())
		        .content(new ObjectMapper().writeValueAsString(item)))
		        .andExpect(status().isOk());
		
	}
	
	//Testing the controller for decreasing the item's purchasing quantity in the cart
	@Test
	public void test_decreaseItem() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		when(cartService.decreaseItem(item)).thenReturn(item);
		mockMvc.perform(put("/api/item/{id}/decrease","1")
				.contentType(MediaType.APPLICATION_JSON)
		        .with(csrf())
		        .content(new ObjectMapper().writeValueAsString(item)))
		        .andExpect(status().isOk());
		
	}
	
	//Testing the controller for removing an item from the cart
	@Test
	public void test_deleteItemFromCart() throws Exception
	{
		Book book=new Book("1","url","Little Brother Lost","Ford","thriller",7299,10);
		AuthUser user=new AuthUser("1","maheen","maheen","user","123456789","hyderabad");	
		Item item=new Item("1",book,user,2,"pending","10 April 2024");
		doNothing().when(cartService).deleteItem("1");
		mockMvc.perform(delete("/api/item/{id}","1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.content(new ObjectMapper().writeValueAsString(item)))
				.andExpect(status().isOk());
	}
	
	
 
  
}
