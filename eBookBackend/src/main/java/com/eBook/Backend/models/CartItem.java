package com.eBook.Backend.models;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;

@Entity
public class CartItem 
{
	@Id
  private int cartItemId;
  private int quantity;
  private double totalPrice;
  
  
  
  
  
  
public int getCartItemId() {
	return cartItemId;
}
public void setCartItemId(int cartItemId) {
	this.cartItemId = cartItemId;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public double getTotalPrice() {
	return totalPrice;
}
public void setTotalPrice(double totalPrice) {
	this.totalPrice = totalPrice;
}
  
  
  
}
