package com.eBook.Backend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.service.CartServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/item/")
@CrossOrigin(origins = "*")
public class CartController {
	@Autowired
	private CartServiceImpl cartServiceImpl;
	
	
	
	@PostMapping("/addToCart")
	public ResponseEntity<Item> addItemToCart(@RequestBody Item item)
	{
		item.setStatus("added to cart");
		return ResponseEntity.ok(cartServiceImpl.addItemToCart(item));
	}
	
	@PostMapping("/addToOrder")
	public ResponseEntity<Item> addItemToOrders(@RequestBody Item item)
	{
		item.setStatus("pending");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		item.setDate(dateFormat.format(new Date()));
		return ResponseEntity.ok(cartServiceImpl.addItemToOrders(item));
	}
	
	
	
	
	
	
	
	@PutMapping("/updateStatus/{status}")
	public ResponseEntity<String> updateItemStatus(@RequestBody Item item, @PathVariable("status")String newStatus)
	{
		cartServiceImpl.updateItemStatus(item, newStatus);
		return ResponseEntity.ok("status updated");
	}
	
	@PutMapping("/searchByStatus/{status}")
	public ResponseEntity<Set<Item>> searchByUserAndStatus(@RequestBody AuthUser user, @PathVariable("status") String status)
	{
		return ResponseEntity.ok(cartServiceImpl.findItemsByStatusAndUsername(status, user.getUsername()));
	}
	
	@PutMapping("/getOrders")
	public ResponseEntity<Set<Item>> getOrders(@RequestBody AuthUser user)
	{
		Set<Item>orders = cartServiceImpl.findItemsByStatusAndUsername("pending",user.getUsername());
		orders.addAll(cartServiceImpl.findItemsByStatusAndUsername("cancelled",user.getUsername()));
		orders.addAll(cartServiceImpl.findItemsByStatusAndUsername("delivered",user.getUsername()));
		orders.addAll(cartServiceImpl.findItemsByStatusAndUsername("confirmed",user.getUsername()));;
		return ResponseEntity.ok(orders);
	}
	
	
	@PutMapping("{id}/increase")
	public ResponseEntity<Item> increaseItem(@RequestBody Item item, @PathVariable("id") String itemId)
	{
		item.setId(itemId);
		return ResponseEntity.ok(cartServiceImpl.increaseItem(item));
	}
	
	@PutMapping("{id}/decrease")
	public ResponseEntity<Item> decreaseItem(@RequestBody Item item, @PathVariable("id") String itemId)
	{
		item.setId(itemId);
		return ResponseEntity.ok(cartServiceImpl.decreaseItem(item));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteItemFromCart(@PathVariable("id") String itemId)
	{
		cartServiceImpl.deleteItem(itemId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted book succesfully");
		
	}
	
}
