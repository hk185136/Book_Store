package com.eBook.Backend.models;

import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotficationSubscription {

	@Id
	@Indexed
    private String id;
	private AuthUser user;
	private Book book;
	private Item item;
	

	public NotficationSubscription(String id, AuthUser user, Book book, Item item, String notifcationTitle, String description) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.item = item;
	}
	
	
	public NotficationSubscription() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AuthUser getUser() {
		return user;
	}
	public void setUser(AuthUser user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
    
}
