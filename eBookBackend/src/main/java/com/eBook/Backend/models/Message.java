package com.eBook.Backend.models;

import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
	@Id
	@Indexed
	private String id;
	private String date;
	private String message;
	private String username;
	public Message(String id,String date, String message,String username) {
		super();
		this.id=id;
		this.date = date;
		this.message = message;
		this.username = username;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
