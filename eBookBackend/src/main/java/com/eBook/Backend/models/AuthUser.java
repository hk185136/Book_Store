package com.eBook.Backend.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.persistence.Id;


@Data
@Builder
// Class representing a user.
public class AuthUser {
	// Following variables show what is stored inside a user object.
	@Id
	private String id;
	@Indexed
	private String username;
	private String password;
	private String role;
	private String pno;
	private String address;
	
	// No args and all args constructors.
	public AuthUser()
	{
		
	}
	
	
	public AuthUser(String id, String username, String password, String role, String pno, String address) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.pno = pno;
		this.address = address;
	}
	


	
	//Getters and setters for the fields
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
