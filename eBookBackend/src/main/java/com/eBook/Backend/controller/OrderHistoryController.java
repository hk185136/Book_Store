package com.eBook.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Date;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.eBook.Backend.models.OrderHistory;
import com.eBook.Backend.service.OrderHistoryImplementation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;



@RestController
@AllArgsConstructor
@RequestMapping("/api/user/orderHistory/")
@CrossOrigin(origins = "*")
// Class to implement the Rest APIs for order history.
public class OrderHistoryController 
{
	//Autowiring service layer class
	@Autowired
     OrderHistoryImplementation OrderHistoryImpl;
	
	
	// Post request which accepts OrderHistory data, adds it to database and returns that data.
	@PostMapping("/addToHistory")
	public ResponseEntity<OrderHistory> addToHistory(@RequestBody OrderHistory order)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		order.setDate(dateFormat.format(new Date()));
		return ResponseEntity.ok(OrderHistoryImpl.addtoHistory(order));
	}
	
	// Get request which accepts a user name and returns the order history related to that user.	
	@GetMapping("/getOrderHistory/{username}")
	public ResponseEntity<List<OrderHistory>> getOrderHistory(@PathVariable String username)
	{
		List<OrderHistory> orders = OrderHistoryImpl.findOrderHistoryByUsername(username);
		return ResponseEntity.ok(orders);
	}
	
	// Delete request which accepts an order history entry id and removes it and returns a success message.
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteOrderHistoryById(@PathVariable String id)
	{
		OrderHistoryImpl.deleteById(id);
		return ResponseEntity.ok("deleted succesfully");
	}
	
	// Delete request which accepts a user name and removes the entire order history related to that user and returns a success message.
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<Object> deleteOrderHistory(@PathVariable String username)
	{
		OrderHistoryImpl.deleteAll(username);
		return ResponseEntity.ok("deleted succesfully");
	}
	
	
 

}

