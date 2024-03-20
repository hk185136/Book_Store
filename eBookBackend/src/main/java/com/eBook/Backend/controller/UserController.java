package com.eBook.Backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eBook.Backend.Repository.AuthUserRepository;
import com.eBook.Backend.models.AuthUser;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	@Autowired
	private  AuthUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")
	public ResponseEntity registerUser(@RequestBody AuthUser user)
	{
		try {
			if(userRepository.findByusername(user.getUsername()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			AuthUser save = userRepository.save(user);
			return ResponseEntity.ok(HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity loginUser(@RequestBody AuthUser user)
	{
		try {
			Optional<AuthUser> userStored = userRepository.findByusername(user.getUsername());
			if(userStored.isPresent() && passwordEncoder.matches(user.getPassword(), userStored.get().getPassword()))
				return ResponseEntity.status(HttpStatus.CREATED).body("Logged In");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Credentials not found");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	
}
