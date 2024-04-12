package com.eBook.Backend.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@Autowired
	private  AuthenticationManager authenticationManager;
	
	 @Autowired
	private JwtUtil jwtUtil;
	 
	 public AuthController() {
		 
	 }
	
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
			LoginRes registerRes = new LoginRes(save.getUsername(),save.getRole(),"",save.getAddress(), save.getPno());
			return ResponseEntity.ok(registerRes);
		} catch (Exception e) {
			ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@PostMapping("/signin/{role}")
	public ResponseEntity loginUser(@RequestBody AuthUser user,@PathVariable("role")String roleFetched)
	{
		try {
			Optional<AuthUser> userStored = userRepository.findByusername(user.getUsername());
			Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
			String name=authentication.getName();
			String role=userStored.get().getRole();
			if(!role.equals(roleFetched))
				throw new Exception("Invalid username or password");
			String address= userStored.get().getAddress();
			String pno = userStored.get().getPno();
			String token=jwtUtil.createToken(user);
			LoginRes loginRes = new LoginRes(name, role , token, address, pno);
			return ResponseEntity.ok(loginRes);
		}catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	}
	
	
	@PutMapping("/editUser/{name}")
	public ResponseEntity<AuthUser> editUser(@PathVariable("name")String userName, @RequestBody AuthUser user)
	{
	    AuthUser storedUser = userRepository.findByusername(userName).get();
	    storedUser.setPno(user.getPno());
	    storedUser.setAddress(user.getAddress());
	    
	    AuthUser updatedUser =userRepository.save(storedUser);
	    return ResponseEntity.ok(updatedUser);
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<AuthUser>> getAllUsers()
	{
	    List<AuthUser>users = userRepository.findByRole("customer");
	    return ResponseEntity.ok(users);
	}
}
