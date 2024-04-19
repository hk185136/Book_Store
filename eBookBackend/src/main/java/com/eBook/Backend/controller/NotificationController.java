package com.eBook.Backend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Notification;
import com.eBook.Backend.service.CartAndOrderServiceImpl;
import com.eBook.Backend.service.NotificationServiceImpl;
import com.eBook.Backend.service.UserBookImplementation;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {	
	
	@Autowired
	private NotificationServiceImpl notificationServiceImpl;
	@GetMapping(value = "/getNotifications/{username}")
	public ResponseEntity<List<Notification>> getNotificationsByUsername(@PathVariable String username){
		return ResponseEntity.ok(notificationServiceImpl.getNotificationsByUser(username).get());
	}
	@DeleteMapping(value = "/deleteNotification/{id}")
	public ResponseEntity<String> deleteNotificationByid(@PathVariable String id){
		notificationServiceImpl.deleteById(id);
		return ResponseEntity.status(200).body("Deleted successfully");
	}
	@PostMapping(value = "/addNotification")
	public ResponseEntity<Notification> addNotification(@RequestBody Notification notification)
	{
		return ResponseEntity.status(200).body(notificationServiceImpl.addtoNotifications(notification));
	}
	
	@GetMapping(value = "/subscribe/{username}", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@PathVariable String username) {
		return notificationServiceImpl.initUserSubscription(username);
	}
	
	
	@PostMapping(value = "/dispatchStockRefillNotficationToSpecificUser")
	public ResponseEntity<String> dispatchStockRefillBookNotification(@RequestParam String bookname)
	{
		List<Notification>stockRefillNotifications = notificationServiceImpl.getNotificationsByTitle(bookname).get();
		for(Notification stockRefillNotification : stockRefillNotifications) {
			notificationServiceImpl.dispatchNotification(stockRefillNotification.getUser().getUsername(), "Refill stock", stockRefillNotification);
			deleteNotificationByid(stockRefillNotification.getId());
		}
		return ResponseEntity.ok("refill done");
	}
	
	@PostMapping(value = "/dispatchOrderStatusNotficationToSpecificUser")
	public ResponseEntity<String> dispatchOrderStatusNotification(@RequestParam String bookname, @RequestParam String orderStatus)
	{
		List<Notification> orderStatusNotifications = notificationServiceImpl.getNotificationsByStatusAndTitle(orderStatus, bookname).get();
		for(Notification orderStatusNotification : orderStatusNotifications) {
			notificationServiceImpl.dispatchNotification(orderStatusNotification.getUser().getUsername(), "Order Status Changed", orderStatusNotification);
			deleteNotificationByid(orderStatusNotification.getId());
		}
		
		return ResponseEntity.ok("order delivered");
	}
}
