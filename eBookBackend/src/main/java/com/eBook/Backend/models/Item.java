package com.eBook.Backend.models;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
	
	@Id
	@Indexed
	private String id;
	private Book book;
	private AuthUser user;
	private int quantity;
	private String status;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    private String date;
	
	public Item()
	{
		
	}
	
	public Item(String id, Book book, AuthUser user, int quantity, String status, String date) {
		super();
		this.id = id;
		this.book = book;
		this.user = user;
		this.quantity = quantity;
		this.status = status;
		this.date = date;
	}
	
	
	public Item() {
		super();
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public AuthUser getUser() {
		return user;
	}
	public void setUser(AuthUser user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
	
}
