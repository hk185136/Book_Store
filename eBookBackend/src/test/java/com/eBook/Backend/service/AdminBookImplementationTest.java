//package com.eBook.Backend.service;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import com.eBook.Backend.Repository.BookRepository;
//import com.eBook.Backend.models.Book;
//
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class AdminBookImplementationTest
//{
//    @Mock
//    private BookRepository bookRepo;
//    
//    @InjectMocks
//    private AdminBookImplementation adminBookService;
//	
//
//	
//	@Test
//	public void whenSaveBook_shouldReturnBook() {
//	Book book=new Book();
//	book.setTitle("Hanging House");
//	when(bookRepo.save(ArgumentMatchers.any(Book.class))).thenReturn(book);
//	Book created = adminBookService.createBook(book);
//	assertThat(created.getTitle()).isSameAs(book.getTitle());
//	verify(bookRepo).save(book);
//	}
//}
//
