package com.eBook.Backend.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.userdetails.User;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
public class Notification {

	@Id
	@Indexed
    private String id;
	private AuthUser user;
	private Book book;
	private Item item;
	private String notifcationTitle;
	private String description;
	
	
	

	public Notification(String id, AuthUser user, Book book, Item item, String notifcationTitle, String description) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.item = item;
		this.notifcationTitle = notifcationTitle;
		this.description = description;
	}
	
	
	public Notification() {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	public String getNotifcationTitle() {
		return notifcationTitle;
	}

	public void setNotifcationTitle(String notifcationTitle) {
		this.notifcationTitle = notifcationTitle;
	}
	
	
	
    
}
