package com.eBook.Backend.models;


import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.ToString;



@Entity
public class Cart {
    @Id
    private int cartid;

	public int getCartid() {
		return cartid;
	}

	public void setCartid(int cartid) {
		this.cartid = cartid;
	}

	public Cart(int cartid) {
		super();
		this.cartid = cartid;
	}

    
   
}