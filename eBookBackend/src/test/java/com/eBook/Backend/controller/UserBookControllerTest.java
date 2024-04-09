package com.eBook.Backend.controller;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.eBook.Backend.models.Book;
import com.eBook.Backend.service.UserBookImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserBookController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserBookControllerTest
{
  
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserBookImplementation userBookService;
	
	
	
	@Test
	public void test_getBookbyId() throws Exception
	{
		Book book=new Book("1","url1","Hanging House","Ford","horror",1500,10);
		when(userBookService.getBookById("1")).thenReturn(book);
		mockMvc.perform(get("/api/user/books/{id}","1")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.title").value("Hanging House"));
	}
	
	
	
	@Test
	public void test_getBookbyTitle() throws Exception
	{
		List<Book> booklist=Arrays.asList(new Book("1","url1","Hanging House","Ford","horror",1500,10),
				            new Book("2","url2","Lost in the Woods","James","mystery",1599,6));
		Optional<List<Book>> resOptional=Optional.of(booklist);
		when(userBookService.getBookByTitle("Hanging House")).thenReturn(resOptional);
		mockMvc.perform(get("/api/user/books/title/{title}","Hanging House")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].author").value("Ford"));
	}
	
	
	@Test
	public void test_getAllbooks() throws Exception
	{
		List<Book> booklist=Arrays.asList(new Book("1","url1","Hanging House","Ford","horror",1500,10),
				            new Book("2","url2","Lost in the Woods","James","mystery",1599,6));
		when(userBookService.getAllBooks()).thenReturn(booklist);
		mockMvc.perform(get("/api/user/books/")
				.with(csrf()))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$[1].title").value("Lost in the Woods"));
		        
	}
	
	
	@Test
	public void test_getBookByAuthor() throws Exception
	{
		List<Book> booklist=Arrays.asList(new Book("1","url1","Hanging House","Ford","horror",1500,10),
				            new Book("2","url2","Lost in the Woods","James","mystery",1599,6));
		Optional<List<Book>> resOptional=Optional.of(booklist);
		when(userBookService.getBookByAuthor("James")).thenReturn(resOptional);
		mockMvc.perform(get("/api/user/books/author/{author}","James")
				.with(csrf()))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$[1].title").value("Lost in the Woods"));
	}
	
	
	@Test
	public void test_getBookByGenre() throws Exception
	{
		List<Book> booklist=Arrays.asList(new Book("1","url1","Hanging House","Ford","horror",1500,10),
				            new Book("2","url2","Lost in the Woods","James","mystery",1599,6));
		
	    Optional<List<Book>> resOptional=Optional.of(booklist);
	    when(userBookService.getBooksByGenre("horror")).thenReturn(resOptional);
	    mockMvc.perform(get("/api/user/books/genre/{genre}","horror")
	    		.with(csrf()))
	             .andExpect(status().isOk())
	             .andExpect(jsonPath("$[0].title").value("Hanging House"));
	}
	
	
	@Test
	public void test_getBookbyPrice() throws Exception
	{
		List<Book> booklist=Arrays.asList(new Book("1","url1","Hanging House","Ford","horror",1500,10),
				            new Book("2","url2","Lost in the Woods","James","mystery",1599,6));
		
		Optional<List<Book>> resOptional=Optional.of(booklist);
		when(userBookService.getBooksByPrice(1500)).thenReturn(resOptional);
		mockMvc.perform(get("/api/user/books/price/{price}",1500)
				.with(csrf()))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$[0].title").value("Hanging House"));
	}
	
	@Test
	public void test_getBookbyPrice_LessOrEqual() throws Exception
	{
		List<Book> booklist=Arrays.asList(new Book("1","url1","Hanging House","Ford","horror",1500,10),
	            new Book("2","url2","Lost in the Woods","James","mystery",1599,6));

         Optional<List<Book>> resOptional=Optional.of(booklist);
         when(userBookService.getBooksByPriceLessorEqual(1506)).thenReturn(resOptional);
         mockMvc.perform(get("/api/user/books/lessorequalprice/{price}",1506)
	     .with(csrf()))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[0].title").value("Hanging House"));
	}
	
	@Test
	public void test_getBookbyPrice_pricerange() throws Exception
	{
		List<Book> booklist=Arrays.asList(new Book("1","url1","Hanging House","Ford","horror",1500,10),
	            new Book("2","url2","Lost in the Woods","James","mystery",1599,6));

         Optional<List<Book>> resOptional=Optional.of(booklist);
         when(userBookService.getBooksByPriceBetween(1550,1600)).thenReturn(resOptional);
         mockMvc.perform(get("/api/user/books/pricerange/{p1}/{p2}",1550,1600)
	     .with(csrf()))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[1].title").value("Lost in the Woods"));
	}
}








