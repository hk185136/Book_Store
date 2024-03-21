package com.eBook.Backend.models.response;

public class LoginRes {
	 private String name;
	 private String role;
	 private String token;
	 
	 public LoginRes(String name, String role, String token) {
		 this.name = name;
		 this.role=role;
		 this.token = token;
	 }
	 public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	   
	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }
}
