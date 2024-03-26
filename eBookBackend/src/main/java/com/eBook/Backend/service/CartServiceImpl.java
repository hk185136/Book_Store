package com.eBook.Backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.CartRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;

@Service
public class CartServiceImpl {
	
	@Autowired
	private CartRepository CartRepository;
	
	public Set<Item> getAllItems(AuthUser user){
		List<Item>userItems =CartRepository.findByUser(user);
		Set<Item> Items = new HashSet<>();
		Items.addAll(userItems);
		return Items;
	}
	
	public Item addItem(Item Item)
	{
		Item ItemAdded = CartRepository.save(Item);
		return ItemAdded;
	}
	
	public Item increaseItem(Item item) {
		Item storedItem = CartRepository.findById(item.getId()).get();
		storedItem.setQuantity(storedItem.getQuantity()+1);
		return CartRepository.save(storedItem);
	}
	
	public Item decreaseItem(Item item) {	
		Item storedItem = CartRepository.findById(item.getId()).get();
		storedItem.setQuantity(storedItem.getQuantity()-1);
		return CartRepository.save(storedItem);
	}
	
	public void deleteItem(String itemId) {
		CartRepository.deleteById(itemId);
	}
	
	
}
