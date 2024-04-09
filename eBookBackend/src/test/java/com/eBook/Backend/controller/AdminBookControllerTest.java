package com.eBook.Backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eBook.Backend.controller.AdminBookController;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.service.AdminBookImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;



@WebMvcTest(controllers = AdminBookController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class AdminBookControllerTest
{
  
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdminBookImplementation adminBookService;
	
	
	@Test
	public void test_createBook() throws Exception
	{
		Book book=new Book("1","url1","Hanging House","Ford","horror",1500,10);
		when(adminBookService.createBook(book)).thenReturn(book);
		mockMvc.perform(post("/api/admin/books/")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
	            .content(new ObjectMapper().writeValueAsString(book)))
	            .andExpect(status().isOk());
	}
	
	
	@Test
	public void test_updateBook() throws Exception
	{
		Book book=new Book("1","url1","Hanging Train","Ford","horror",1500,10);
		when(adminBookService.updateBook(book)).thenReturn(book);
		mockMvc.perform(put("/api/admin/books/{id}","1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.content(new ObjectMapper().writeValueAsString(book)))
		        .andExpect(status().isOk());
		assertEquals("Hanging Train", book.getTitle());
		        
	}
	
	@Test
	public void test_DeleteBook() throws Exception
	{
		Book book=new Book("1","url1","Hanging Train","Ford","horror",1500,10);
		doNothing().when(adminBookService).deleteBook("1");
		mockMvc.perform(delete("/api/admin/books/{id}","1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.content(new ObjectMapper().writeValueAsString(book)))
				.andExpect(status().isOk());
	}

}
