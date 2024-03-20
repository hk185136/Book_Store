package com.eBook.Backend.service;


import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.AuthUserRepository;
import com.eBook.Backend.models.AuthUser;

@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService{
	 @Autowired
	 private AuthUserRepository userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Optional<AuthUser> authUser = userRepository.findByusername(username.toLowerCase());
	        if (!authUser.isPresent()) {
	            throw new UsernameNotFoundException(username);
	        } else {
	            return User.builder()
	                    .username(authUser.get().getUsername())
	                    .password(authUser.get().getPassword())
	                    .roles(authUser.get().getRole())
	                    .build();
	        }
	    }
}
