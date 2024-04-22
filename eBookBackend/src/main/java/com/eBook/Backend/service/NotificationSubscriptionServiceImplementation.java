package com.eBook.Backend.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.eBook.Backend.Repository.NotificationSubscriptionRepository;
import com.eBook.Backend.models.NotificationSubscription;

@Service
// Offers service related to subscription of a user.
public class NotificationSubscriptionServiceImplementation {
	
	// Auto wiring the repository layer class
	@Autowired
	private NotificationSubscriptionRepository notificationSubscriptionRepository;

	// Map holding the user name and server sent event object(used to enable subscription).
	public Map<String, SseEmitter> emitters = new HashMap<>();
	
	
	// Takes a subscription, saves it to the database.
	public NotificationSubscription addtoSubscriptions(NotificationSubscription notificationSubscription) {
		return notificationSubscriptionRepository.save(notificationSubscription);
	}	
	
	// Takes in an id and deletes subscription with that id.
	public void deleteSubscriptionById(String id) {
		notificationSubscriptionRepository.deleteById(id);
	}
	
	// Fetches subscriptions based the book title stored in that subscription.
	public Optional<List<NotificationSubscription>> getSubscriptionsByTitle(String title)
	{
		return notificationSubscriptionRepository.findByTitle(title);
	}
	
	// Fetches subscriptions related to a user. 
	public Optional<List<NotificationSubscription>> getSubscriptionsByUsename(String username)
	{
		return notificationSubscriptionRepository.findByUsername(username);
	}
	
	// Enables subscriptions for a particular user.
	public SseEmitter initiateUserSubscription(String username)
	{
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("Initiate subscription"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		emitters.put(username, sseEmitter);
		return sseEmitter;
	}
}
