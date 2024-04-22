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
public class NotificationSubscriptionServiceImplementation {
	@Autowired
	private NotificationSubscriptionRepository notificationSubscriptionRepository;

	public Map<String, SseEmitter> emitters = new HashMap<>();
	
	public NotificationSubscription addtoSubscriptions(NotificationSubscription notificationSubscription) {
		return notificationSubscriptionRepository.save(notificationSubscription);
	}	
	
	public void deleteSubscriptionById(String id) {
		notificationSubscriptionRepository.deleteById(id);
	}
	
	
	public Optional<List<NotificationSubscription>> getSubscriptionsByTitle(String title)
	{
		return notificationSubscriptionRepository.findByTitle(title);
	}
	
	public Optional<List<NotificationSubscription>> getSubscriptionsByStatusAndTitle(String status, String title)
	{
		return notificationSubscriptionRepository.findByStatusAndTitle(status, title);
	}
	
	public Optional<List<NotificationSubscription>> getSubscriptionsByUsename(String username)
	{
		return notificationSubscriptionRepository.findByUsername(username);
	}
	
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
