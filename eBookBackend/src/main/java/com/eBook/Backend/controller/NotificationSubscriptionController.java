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
import com.eBook.Backend.models.NotficationSubscription;
import com.eBook.Backend.service.CartAndOrderServiceImpl;
import com.eBook.Backend.service.NotificationSubscriptionServiceImplementation;
import com.eBook.Backend.service.UserBookImplementation;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/subscription/")
@CrossOrigin(origins = "*")
public class NotificationSubscriptionController {	
	
	@Autowired
	private NotificationSubscriptionServiceImplementation notificationSubscriptionServiceImplementation;
	
	@DeleteMapping("deleteSubscription/{id}")
	public ResponseEntity<String> deleteSubscriptionByid(@PathVariable String id){
		notificationSubscriptionServiceImplementation.deleteSubscriptionById(id);
		return ResponseEntity.status(200).body("Deleted subscription successfully");
	}
	
	@PostMapping("addSubscription")
	public ResponseEntity<NotficationSubscription> addSubscription(@RequestBody NotficationSubscription notificationSubscription)
	{
		return ResponseEntity.status(200).body(notificationSubscriptionServiceImplementation.addtoSubscriptions(notificationSubscription));
	}
	
	@GetMapping(value = "enableSubscription/{username}", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@PathVariable String username) {
		return notificationSubscriptionServiceImplementation.initiateUserSubscription(username);
	}
}
