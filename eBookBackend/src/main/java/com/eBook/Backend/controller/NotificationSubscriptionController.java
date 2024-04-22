package com.eBook.Backend.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.eBook.Backend.models.NotificationSubscription;
import com.eBook.Backend.service.NotificationSubscriptionServiceImplementation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/subscription/")
@CrossOrigin(origins = "*")
// Class to manage subscription for notifications.
public class NotificationSubscriptionController {	
	
	// Auto wiring service layer class
	@Autowired
	private NotificationSubscriptionServiceImplementation notificationSubscriptionServiceImplementation;
	
	
	// Get request which accepts user name, fetches all the subscriptions related to that user. 
	@GetMapping("getSubscriptions/{username}")
	public List<NotificationSubscription> getSubscriptionsByUsername(@PathVariable String username){
		return notificationSubscriptionServiceImplementation.getSubscriptionsByUsename(username).get();
	}
	
	// Delete request which takes an id, deletes a subscription with that id and returns a success message.
	@DeleteMapping("deleteSubscription/{id}")
	public ResponseEntity<String> deleteSubscriptionById(@PathVariable String id){
		notificationSubscriptionServiceImplementation.deleteSubscriptionById(id);
		return ResponseEntity.status(200).body("Deleted subscription successfully");
	}
	
	// Post request which takes a subscription, adds that to database.
	@PostMapping("addSubscription")
	public ResponseEntity<NotificationSubscription> addSubscription(@RequestBody NotificationSubscription notificationSubscription)
	{
		return ResponseEntity.status(200).body(notificationSubscriptionServiceImplementation.addtoSubscriptions(notificationSubscription));
	}
	
	
	// Get request which takes a user name, enables the subscriptions.
	@GetMapping(value = "enableSubscription/{username}", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@PathVariable String username) {
		return notificationSubscriptionServiceImplementation.initiateUserSubscription(username);
	}
}
