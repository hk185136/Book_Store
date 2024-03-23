package com.eBook.Backend.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.eBook.Backend.Repository.AuthUserRepository;
import com.eBook.Backend.auth.JwtUtil;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.response.ErrorRes;
import com.eBook.Backend.models.response.LoginRes;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	@Autowired
	private  AuthUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 private  AuthenticationManager authenticationManager;
	
	 @Autowired
	private JwtUtil jwtUtil;
	
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }
	
	@PostMapping("/signup")
	public ResponseEntity registerUser(@RequestBody AuthUser user)
	{
		try {
			if(userRepository.findByusername(user.getUsername()).isPresent())
			{
				ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Username already taken");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			AuthUser save = userRepository.save(user);
			LoginRes registerRes = new LoginRes(save.getUsername(),save.getRole(),"");
			return ResponseEntity.ok(registerRes);
		} catch (Exception e) {
			ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity loginUser(@RequestBody AuthUser user)
	{
		try {
			Optional<AuthUser> userStored = userRepository.findByusername(user.getUsername());
			Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
			String name=authentication.getName();
			String role=userStored.get().getRole();
			String token=jwtUtil.createToken(user);
			LoginRes loginRes = new LoginRes(name, role , token);
			return ResponseEntity.ok(loginRes);
		}catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	}
	
	
}
