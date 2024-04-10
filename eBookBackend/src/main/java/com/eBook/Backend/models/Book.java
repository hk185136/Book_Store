package com.eBook.Backend.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
	@Id
	private String id;
	private String url;
	@Indexed
	private String title;
	private String author;
	private String genre;
	private double price;
	private int availableQuantity;
	
	public Book()
	{
		
	}
	
	public Book(String id, String url, String title, String author, String genre, double price, int availableQuantity) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.price = price;
		this.availableQuantity = availableQuantity;
	}

	
	
	public Book() {
		super();
	}



	public String getId() {
		return id;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	
	
	

}
