package com.eBook.Backend.controller;


import org.springframework.beans.factory.annotation.Autowired;

import com.eBook.Backend.config.OrderStatusConfig;
import com.eBook.Backend.models.AuthUser;
import lombok.AllArgsConstructor;
import com.eBook.Backend.service.CartAndOrderServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.OrderHistory;
import com.eBook.Backend.service.OrderHistoryImplementation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;




@RestController
@AllArgsConstructor
@RequestMapping("/api/item/")
@CrossOrigin(origins = "*")
// Class to implement Rest APIs for managing orders.
public class CartAndOrderController {
	
	//Autowiring service and repository layer classes.
	@Autowired
	private CartAndOrderServiceImpl cartAndOrderServiceImpl;
	@Autowired
	private OrderHistoryImplementation OrderHistoryImplementation;
	
	
	//Post request which accepts item data, sets the item status added to cart, stores it in database and returns a response with that item data.
	@PostMapping("/addToCart")
	public ResponseEntity<Item> addItemToCart(@RequestBody Item item)
	{
		item.setStatus(OrderStatusConfig.addToCart);
		return ResponseEntity.ok(cartAndOrderServiceImpl.addItem(item));
	}
	
	//Post request which accepts item data, automates the status of item, adds an entry to history, stores those in database and returns a response with that item data.
	@PostMapping("/addToOrder")
	public ResponseEntity<Item> addItemToOrders(@RequestBody Item item)
	{
		cartAndOrderServiceImpl.addItem(item);
		cartAndOrderServiceImpl.performOrderStep(item, OrderStatusConfig.confirmed, 1000);
		cartAndOrderServiceImpl.performOrderStep(item, OrderStatusConfig.onTheWay, 15000);
		cartAndOrderServiceImpl.performOrderStep(item, OrderStatusConfig.delivered, 25000);		
		return ResponseEntity.ok(item);
	}
		
	
	//Put request which accepts item data, status to be updated with and returns a success response.
	@PutMapping("/updateStatus/{status}")
	public ResponseEntity<String> updateItemStatus(@RequestBody Item item, @PathVariable("status")String newStatus)
	{
		cartAndOrderServiceImpl.updateItemStatus(item, newStatus);
		OrderHistory history = OrderHistoryImplementation.setItemToHistory(item);
		OrderHistoryImplementation.addtoHistory(history);
		return ResponseEntity.ok("status updated");
	}
	
	// Put request which accepts user data, status and returns a list of items related to that user and status.
	@PutMapping("/searchByStatus/{status}")
	public ResponseEntity<Set<Item>> searchByUserAndStatus(@RequestBody AuthUser user, @PathVariable("status") String status)
	{
		return ResponseEntity.ok(cartAndOrderServiceImpl.findItemsByStatusAndUsername(status, user.getUsername()));
	}
	
	// Put request which accepts user data and returns all the items related to that user.
	@PutMapping("/getOrders")
	public ResponseEntity<Set<Item>> getOrders(@RequestBody AuthUser user)
	{
		Set<Item>orders = cartAndOrderServiceImpl.findItemsByStatusAndUsername(OrderStatusConfig.onTheWay,user.getUsername());
		orders.addAll(cartAndOrderServiceImpl.findItemsByStatusAndUsername(OrderStatusConfig.cancelled,user.getUsername()));
		orders.addAll(cartAndOrderServiceImpl.findItemsByStatusAndUsername(OrderStatusConfig.delivered,user.getUsername()));
		orders.addAll(cartAndOrderServiceImpl.findItemsByStatusAndUsername(OrderStatusConfig.confirmed,user.getUsername()));;
		return ResponseEntity.ok(orders);
	}
	
	// Put request which accepts item data, item id, increases that item quantity by one.
	@PutMapping("{id}/increase")
	public ResponseEntity<Item> increaseItem(@RequestBody Item item, @PathVariable("id") String itemId)
	{
		item.setId(itemId);
		return ResponseEntity.ok(cartAndOrderServiceImpl.increaseItem(item));
	}
	
	// Put request which accepts item data, item id, decreases that item quantity by one.
	@PutMapping("{id}/decrease")
	public ResponseEntity<Item> decreaseItem(@RequestBody Item item, @PathVariable("id") String itemId)
	{
		item.setId(itemId);
		return ResponseEntity.ok(cartAndOrderServiceImpl.decreaseItem(item));
	}
	
	// Delete request which accepts an item id, deletes the item with that id and returns a success message.
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteItemFromCart(@PathVariable("id") String itemId)
	{
		cartAndOrderServiceImpl.deleteItem(itemId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted book succesfully");
		
	}
	
}
