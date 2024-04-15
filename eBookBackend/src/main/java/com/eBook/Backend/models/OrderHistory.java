package com.eBook.Backend.models;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//Class representing Order history.
public class OrderHistory {
	
	@Id
	// Following variables show what is stored inside a order history object.
	private String id;
	private Item item;
	private String date;
	
	// No args and all args constructors.
	public OrderHistory()
	{
		
	}
	
	//Getters and setters for the fields
	public OrderHistory(String id, Item item, String date) {
		super();
		this.id = id;
		this.item = item;
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
