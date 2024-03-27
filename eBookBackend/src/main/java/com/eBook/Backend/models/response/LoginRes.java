package com.eBook.Backend.models.response;

public class LoginRes {
	 private String name;
	 private String role;
	 private String token;
	 private String address;
	 private String pno;
	 
	 public LoginRes(String name, String role, String token, String address, String pno) {
		 this.name = name;
		 this.role=role;
		 this.token = token;
		 this.address=address;
		 this.pno=pno;
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
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getPno() {
			return pno;
		}
		public void setPno(String pno) {
			this.pno = pno;
		}
	    
	    
}

