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
// Uses spring's user details service class to perform user authentication.
public class AuthUserImplementation implements UserDetailsService{
	 @Autowired
	 private AuthUserRepository userRepository;

	 // load user details based on username.
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
