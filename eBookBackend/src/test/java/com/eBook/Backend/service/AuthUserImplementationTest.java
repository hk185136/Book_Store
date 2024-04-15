package com.eBook.Backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.eBook.Backend.Repository.AuthUserRepository;
import com.eBook.Backend.models.AuthUser;

@SpringBootTest
public class AuthUserImplementationTest {
	@Autowired
	private AuthUserImplementation authUserImplementation;
	
	@MockBean
	private AuthUserRepository userRepository;
	
	AuthUser user1 = new AuthUser("1", "ranjan", "1234678", "customer", "123456789", "hyderabad");
	AuthUser user2 = new AuthUser("2", "ranjanreddy", "1234678", "admin", "1234567810", "hyderabad");
	
	@BeforeEach
	public void setUp()
	{
		when(userRepository.findByusername("Ranjan")).thenReturn(Optional.of(user1));
		when(userRepository.findByusername("ranjan")).thenReturn(Optional.of(user1));
	}
	
	@Test
	public void loadUserByUsername()
	{
		UserDetails userActual = authUserImplementation.loadUserByUsername("ranjan");
		assertEquals(user1.getUsername(),userActual.getUsername());
	}
}
