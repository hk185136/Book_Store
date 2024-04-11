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

import java.util.Optional;

import org.hamcrest.CoreMatchers;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eBook.Backend.Repository.AuthUserRepository;
import com.eBook.Backend.config.SecurityConfig;
import com.eBook.Backend.controller.AdminBookController;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.service.AdminBookImplementation;
import com.eBook.Backend.service.AuthUserImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;



@WebMvcTest(controllers = AuthUserControllerTest.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class AuthUserControllerTest
{
  
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthUserImplementation authUserImplementation;
	
	@MockBean
	private AuthUserRepository authUserRepository;

	@Test
	public void test_createUser() throws Exception
	{
		AuthUser user = new AuthUser("1", "ranjan", "1234678", "customer", "123456789", "hyderabad");
		when(authUserRepository.findByusername(user.getUsername())).thenReturn(Optional.of(user));
		mockMvc.perform(post("/api/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
	            .content(new ObjectMapper().writeValueAsString(user)))
	            .andExpect(status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(user.getUsername())))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.role", CoreMatchers.is(user.getRole())));
	}

}
