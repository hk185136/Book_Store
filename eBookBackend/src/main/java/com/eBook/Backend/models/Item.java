package com.eBook.Backend.models;


import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.persistence.Id;


@Data
@Builder
//Class representing a Item.
public class Item {
	
	@Id
	@Indexed
	// Following variables show what is stored inside a item object.
	private String id;
	private Book book;
	private AuthUser user;
	private int quantity;
	private String status;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    private String date;
	
	
	// No args and all args constructors.
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


	//Getters and setters for the fields
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
