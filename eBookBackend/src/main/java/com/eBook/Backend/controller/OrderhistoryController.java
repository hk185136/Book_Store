package com.eBook.Backend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.eBook.Backend.models.Orderhistory;
import com.eBook.Backend.service.OrderhistoryImplementation;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/orderhistory/")
@CrossOrigin(origins = "*")
public class OrderhistoryController 
{
	@Autowired
     OrderhistoryImplementation orderhistoryImpl;
	
	@PostMapping("/addToHistory")
	public ResponseEntity<Orderhistory> addItemToHistory(@RequestBody Orderhistory order)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		order.setDate(dateFormat.format(new Date()));
		return ResponseEntity.ok(orderhistoryImpl.addtoHistory(order));
	}
	
	
	@GetMapping("/getOrderHistory/{username}")
	public ResponseEntity<List<Orderhistory>> getOrderHistory(@PathVariable String username)
	{
		List<Orderhistory> orders = orderhistoryImpl.findOrderhistoryByUsername(username);
		return ResponseEntity.ok(orders);
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<List<Orderhistory>> deleteOrderHistoryById(@PathVariable String id)
	{
		orderhistoryImpl.deleteById(id);
		return ResponseEntity.ok(null);
	}
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<List<Orderhistory>> deleteOrderHistory(@PathVariable String username)
	{
		orderhistoryImpl.deleteAll(username);
		return ResponseEntity.ok(null);
	}
	
	
 

}

