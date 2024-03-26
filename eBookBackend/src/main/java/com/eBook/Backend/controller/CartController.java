package com.eBook.Backend.controller;

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
@RequestMapping("/api/user/cart/")
@CrossOrigin(origins = "*")
public class CartController {
	@Autowired
	private CartServiceImpl cartServiceImpl;
	
	@GetMapping
	public ResponseEntity<Set<Item>> getAllItems(@RequestBody AuthUser user)
	{
		return ResponseEntity.ok(cartServiceImpl.getAllItems(user));
	}
	
	
	@PostMapping
	public ResponseEntity<Item> addItem(@RequestBody Item item)
	{
		return ResponseEntity.ok(cartServiceImpl.addItem(item));
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
	public ResponseEntity<String> deleteItem(@PathVariable("id") String itemId)
	{
		cartServiceImpl.deleteItem(itemId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted book succesfully");
		
	}
}
