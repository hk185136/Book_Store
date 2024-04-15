package com.eBook.Backend.controller;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.Repository.AuthUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.eBook.Backend.models.response.ErrorRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import com.eBook.Backend.auth.JwtUtil;
import java.util.List;
import com.eBook.Backend.models.response.AuthUserRes;
import lombok.NoArgsConstructor;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;




@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
// Class to implement rest APIs for user authentication and authorization.
public class AuthController {
	// Autowiring repository layer class, password encoding class, spring's authentication manage class and token generation class.
	@Autowired
	private  AuthUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private  AuthenticationManager authenticationManager;
	
	 @Autowired
	private JwtUtil jwtUtil;
	
	 
	 // Post request which accepts user data, creates, stores and returns new user data if that user name does not exist in database else returns an error response with a message.
	@PostMapping("/signup")
	public ResponseEntity<Object> registerUser(@RequestBody AuthUser user)
	{
		try {
			if(userRepository.findByusername(user.getUsername()).isPresent())
			{
				ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Username already taken");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			AuthUser save = userRepository.save(user);
			AuthUserRes registerRes = new AuthUserRes(save.getUsername(),save.getRole(),"",save.getAddress(), save.getPno());
			return ResponseEntity.ok(registerRes);
		} catch (Exception e) {
			ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	// Post request which accepts user data, role(customer/admin), returns user data as response if that user name is already present in database and role matches with that existing user's role else returns error response with a message. 
	@PostMapping("/signin/{role}")
	public ResponseEntity<Object> loginUser(@RequestBody AuthUser user,@PathVariable("role")String roleFetched)
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
			AuthUserRes loginRes = new AuthUserRes(name, role , token, address, pno);
			return ResponseEntity.ok(loginRes);
		}catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	}
	
	
	
	// Put request which accepts user data, user name and returns the updated user response.
	@PutMapping("/editUser/{name}")
	public ResponseEntity<AuthUser> editUser(@PathVariable("name")String userName, @RequestBody AuthUser user)
	{
	    AuthUser storedUser = userRepository.findByusername(userName).get();
	    storedUser.setPno(user.getPno());
	    storedUser.setAddress(user.getAddress());
	    
	    AuthUser updatedUser =userRepository.save(storedUser);
	    return ResponseEntity.ok(updatedUser);
	}
	
	//Get request which returns a list of all users stored in the database.
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<AuthUser>> getAllUsers()
	{
	    List<AuthUser>users = userRepository.findByRole("customer");
	    return ResponseEntity.ok(users);
	}
}
