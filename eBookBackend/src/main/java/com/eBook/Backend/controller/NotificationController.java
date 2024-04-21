package com.eBook.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eBook.Backend.models.NotficationSubscription;
import com.eBook.Backend.models.Notification;
import com.eBook.Backend.service.NotificationServiceImplementation;
import com.eBook.Backend.service.NotificationSubscriptionServiceImplementation;

import lombok.AllArgsConstructor;



@RestController
@AllArgsConstructor
@RequestMapping("/api/user/notification/")
@CrossOrigin(origins = "*")
public class NotificationController {
	@Autowired
	private NotificationServiceImplementation notificationServiceImplementation;
	
	@Autowired
	private NotificationSubscriptionServiceImplementation notificationSubscriptionServiceImplementation;
	
	
	@GetMapping("getNotfications/{username}")
	public ResponseEntity<List<Notification>> getNotficationsByUsername(@PathVariable String username){
		return ResponseEntity.ok(notificationServiceImplementation.getNotificationByUsername(username).get());
	}
	
	@DeleteMapping("deleteNotification/{id}")
	public ResponseEntity<String> deleteNotificationById(@PathVariable String id){
		notificationServiceImplementation.deleteNotificationById(id);
		return ResponseEntity.status(200).body("Deleted successfully");
	}
	
	@PostMapping("/dispatchBookStockRefillNotfications")
	public ResponseEntity<String> dispatchBookStockRefillNotifications(@RequestParam String bookname)
	{
		List<NotficationSubscription>notifyMeSubscriptions = notificationSubscriptionServiceImplementation.getSubscriptionsByTitle(bookname).get();
		for(NotficationSubscription subscription : notifyMeSubscriptions) {
			Notification refillNotification = notificationServiceImplementation.addNotifcation(subscription.getUser().getUsername(), subscription.getBook().getTitle()+"is available now");
			notificationServiceImplementation.dispatchNotification(notificationSubscriptionServiceImplementation.emitters,"Refill stock", refillNotification);
			notificationSubscriptionServiceImplementation.deleteSubscriptionById(subscription.getId());
		}
		return ResponseEntity.ok(bookname+"is in stock");
	}
	
	@PostMapping(value = "/dispatchOrderStatusNotfications")
	public ResponseEntity<String> dispatchOrderStatusNotification(@RequestParam String bookname, @RequestParam String orderStatus)
	{
		
		
		return ResponseEntity.ok(bookname+" is "+orderStatus);
	}
}
