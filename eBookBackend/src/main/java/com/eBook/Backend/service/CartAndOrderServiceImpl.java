package com.eBook.Backend.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.eBook.Backend.Repository.CartAndOrderRepository;

import com.eBook.Backend.Repository.OrderHistoryRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;

@Service
//Class which provides user defined CRUD operations for cart and orders.
public class CartAndOrderServiceImpl {
	
	@Autowired
	private CartAndOrderRepository CartRepository;
	
	// Saves an input item to database
	public Item addItem(Item Item)
	{
		Item itemAddedTocart = CartRepository.save(Item);
		return itemAddedTocart;
	}
	
	
	//Increases item quantity count by one.
	public Item increaseItem(Item item) {
		Item storedItem = CartRepository.findById(item.getId()).get();
		storedItem.setQuantity(storedItem.getQuantity()+1);
		return CartRepository.save(storedItem);
	}
	
	//Decreases item quantity count by one.
	public Item decreaseItem(Item item) {	
		Item storedItem = CartRepository.findById(item.getId()).get();
		storedItem.setQuantity(storedItem.getQuantity()-1);
		return CartRepository.save(storedItem);
	}
	
	//Deletes an item.
	public void deleteItem(String itemId) {
		CartRepository.deleteById(itemId);
	}
	
	// Returns a set of items related to input user name and status.
	public Set<Item> findItemsByStatusAndUsername(String status, String username)
	{
		List<Item>userItems =CartRepository.findByStatusAndUsername(status,username);
		Set<Item> Items = new HashSet<>();
		Items.addAll(userItems);
		return Items;
	}
	
	// Updates an existing item with new status.
	public Item updateItemStatus(Item item, String status)
	{
		Item storedItem  =  CartRepository.findById(item.getId()).get();
		storedItem.setStatus(status);
		CartRepository.save(storedItem);
		return storedItem;
	}
	
	
}
