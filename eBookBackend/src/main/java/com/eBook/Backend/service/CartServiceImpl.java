package com.eBook.Backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.eBook.Backend.Repository.CartRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;

@Service
public class CartServiceImpl {
	
	@Autowired
	private CartRepository CartRepository;
	
	public Item addItemToCart(Item Item)
	{
		Item itemAddedTocart = CartRepository.save(Item);
		return itemAddedTocart;
	}
	
	public Item addItemToOrders(Item Item)
	{
		Item ItemAddedToorders = CartRepository.save(Item);
		return ItemAddedToorders;
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
	
	public Set<Item> findItemsByUserAndStatus(AuthUser user, String status)
	{
		List<Item>userItems =CartRepository.findByStatusAndUser(status, user);
		Set<Item> Items = new HashSet<>();
		Items.addAll(userItems);
		return Items;
	}
	
	
}
